import google.generativeai as genai
import requests
from PIL import Image
from io import BytesIO

genai.configure(api_key="AIzaSyBFQIiEOMXdInD9USFg_Uvi6MtTjl8-ngc")

model = genai.GenerativeModel("gemini-1.5-flash")

img_url = "https://brandlogos.net/wp-content/uploads/2020/11/nike-swoosh-logo-512x512.png"
response = requests.get(img_url)
image = Image.open(BytesIO(response.content))

prompt = "Describe this logo in a sentence."

try:
    gemini_response = model.generate_content([prompt, image])
    print("Gemini response:", gemini_response.text)
except Exception as e:
    print("Gemini failed:", e)
