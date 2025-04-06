import requests

response = requests.post("http://localhost:8000/identify-symbol", json={
    "input": "https://brandlogos.net/wp-content/uploads/2020/11/nike-swoosh-logo-512x512.png",
    "base64": None
})

print(response.json())
