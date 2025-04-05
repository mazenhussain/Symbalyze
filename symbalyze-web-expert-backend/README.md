Web Expert Usage

Important Information 
- The backend server is seperate from the backend server of android studio, since it uses python and android studio isnt optimized for it
- The backend server needs to be run on whichever IDE you choose 

To Run
- Download Dependencies, pip install (if needed, pip install fastapi uvicorn google-generativeai pillow requests)
- I usually run on port 8000, run this command in powershell uvicorn main:app --reload --host 0.0.0.0 --port 8000
- Android accessess the FastAPI backend through a special endpoint: http://10.0.2.2:PORT
- You can test functionality by running the server and then running the python test_request.py script
- It works with a text description or an image URL
