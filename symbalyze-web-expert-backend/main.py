from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
import requests
import google.generativeai as genai
from PIL import Image
from io import BytesIO

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)

# keys
SERP_API_KEY = "7d4b9ad0b80819fe33fd6212667fa3033438071cff092737f8cf8902ba749750"
GEMINI_API_KEY = "AIzaSyBFQIiEOMXdInD9USFg_Uvi6MtTjl8-ngc"
genai.configure(api_key=GEMINI_API_KEY)

# for android 
class IdentifyRequest(BaseModel):
    input: str
    base64: str | None = None  

@app.post("/identify-symbol")
async def identify_symbol(request: IdentifyRequest):
    query = request.input.strip()
    is_image = query.startswith("http://") or query.startswith("https://")

    try:
        # Check if its an image link or a text description 
        if is_image:
            # get the image
            response = requests.get(query)
            image = Image.open(BytesIO(response.content))

            # Use Gemini to describe the image 
            vision_model = genai.GenerativeModel("gemini-1.5-flash")
            prompt = "Describe this logo in a sentence."

            print("Gemini Vision running on image...")
            gemini_response = vision_model.generate_content([prompt, image])
            visual_description = gemini_response.text.strip()
        else:
            visual_description = query

        print(f"Visual Description: {visual_description}")

         # Use SerpAPI to search Google based on that description
        search_query = f"{visual_description} symbol meaning"
        print(f"Search Query: {search_query}")

        search_params = {
            "engine": "google",
            "q": f"{visual_description} symbol meaning",
            "api_key": SERP_API_KEY
        }
        serp_response = requests.get("https://serpapi.com/search", params=search_params)
        data = serp_response.json()

        titles = [res.get("title", "") for res in data.get("organic_results", [])[:5]]
        combined_text = " ".join(titles)

        # printing the top 5 search results from the web 
        print("Top 5 Search Result Titles:")
        for title in titles:
            print(f"- {title}")

        # Use Gemini again to extract a brand or symbol name from the results
        model = genai.GenerativeModel("gemini-1.5-flash")
        summary_prompt = f"From the following search result snippets: \"{combined_text}\", extract the single most likely brand or symbol name being described. Only return the name."

        summary_response = model.generate_content(summary_prompt)
        symbol = summary_response.text.strip()

        # final return statement
        return {
            "symbol": symbol,
            "context": visual_description
        }

    except Exception as e:
        print(f"❌ Error: {e}")
        return {
            "symbol": "Error extracting symbol",
            "context": "N/A"
        }
