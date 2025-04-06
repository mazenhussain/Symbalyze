import requests

url = "https://brandlogos.net/wp-content/uploads/2020/11/nike-swoosh-logo-512x512.png" 
response = requests.get(url)
print("Status Code:", response.status_code)
print("Content-Type:", response.headers.get("Content-Type"))
