import requests
import random
from faker import Faker
import string

faker = Faker('vi_VN')

API_URL = "http://localhost:8088/busbooking/busoperator"
JWT_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJidXMtdGlja2V0Iiwic3ViIjoiYWRtaW4iLCJleHAiOjE3NTI2OTUwMTEsImlhdCI6MTc1MjY5MTQxMSwic2NvcGUiOiJBRE1JTiJ9.hQHDvMbhY_NosPw4IPMfzK_gce7VRwC6UmJJy1-8oxlAM1pAswGmRAombzv5Lh4B_6fa-fPn03wrRXjyX3TpUg"

headers = {
    "Authorization": f"Bearer {JWT_TOKEN}",
    "Content-Type": "application/json"
}

# Một số ảnh xe bus random từ Unsplash hoặc Google Images
bus_images = [
    "https://images.unsplash.com/photo-1503376780353-7e6692767b70",
    "https://images.unsplash.com/photo-1584473457403-3853f8d8b38b",
    "https://images.unsplash.com/photo-1558980394-0fcacc58f1e9",
    "https://images.unsplash.com/photo-1570129477492-45c003edd2be",
    "https://images.unsplash.com/photo-1563973654123-4b40e76aa1a2",
    "https://images.unsplash.com/photo-1627987429309-5d9604b939d3",
    "https://images.unsplash.com/photo-1526481280692-5158e3cb2c0e",
    "https://images.unsplash.com/photo-1599577189727-79b6f6e21e90",
    "https://images.unsplash.com/photo-1616775991800-35b7f1117457",
    "https://images.unsplash.com/photo-1632835222261-97b292d74b68"
]

def generate_phone_number():
    return '0' + ''.join(random.choices(string.digits, k=9))

for i in range(20):
    operator = {
        "busOperatorName": faker.company(),
        "contactPhone": generate_phone_number(),
        "imgUrl": random.choice(bus_images)
    }

    response = requests.post(API_URL, json=operator, headers=headers)
    if response.status_code == 200:
        print(f"✅ Created operator: {operator['busOperatorName']}")
    else:
        print(f"❌ Failed: {response.status_code}, {response.text}")
