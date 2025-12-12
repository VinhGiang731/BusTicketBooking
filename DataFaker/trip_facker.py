import requests
import random
from faker import Faker
from datetime import datetime, timedelta

faker = Faker('vi_VN')

API_URL = "http://localhost:8088/busbooking/trip"
JWT_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJidXMtdGlja2V0Iiwic3ViIjoiYWRtaW4iLCJleHAiOjE3NTI2OTUwMTEsImlhdCI6MTc1MjY5MTQxMSwic2NvcGUiOiJBRE1JTiJ9.hQHDvMbhY_NosPw4IPMfzK_gce7VRwC6UmJJy1-8oxlAM1pAswGmRAombzv5Lh4B_6fa-fPn03wrRXjyX3TpUg"

headers = {
    "Authorization": f"Bearer {JWT_TOKEN}",
    "Content-Type": "application/json"
}

bus_operator_ids = [
    "1aaad2e0-c7fb-4a55-854d-23b7dbcd04aa",
    "1c72fbdb-ef9f-4a52-a937-bcab5cb8e351",
    "40969f00-9067-4bbc-a545-23b5a9f02abe",
    "4b8f34a4-907c-42bd-8f5f-392048ebb7cb",
    "672da2cb-29b4-4b32-b9f6-8bbe9f989f24",
    "78197a99-ddee-4854-92e3-b971c117b148",
    "7b036f73-c67c-40f1-945d-1290bff01423",
    "89b922f5-4e0a-4bff-b816-d514e95770d5",
    "8bade62a-2820-43ed-ac22-70d067972a4c",
    "95036cbc-71ba-4fb9-9706-570acdd5d361",
    "aa9d40a0-e66f-4fad-ad9a-32910be6ec97",
    "ab2b4c68-e332-4bce-b685-d4d89a6535ad",
    "b7cb9e02-e100-4695-8d98-85af04cc53af",
    "b8fde15f-ad04-4a66-b760-a3612ed93be8",
    "bb17a6dc-d22d-44e6-90ee-bc73717cf668",
    "c598d214-ced8-4cf7-94ca-14435756c753",
    "c647a0bf-01fb-4658-9904-2b4f11fc5873",
    "d3bbfc97-35e8-4b58-909b-0134b15d49f1",
    "e0453229-d71f-47a4-bba8-b7088eb850dc",
    "e3958729-aa0a-471d-9618-486fb35a728d"
]

locations = ["Hà Nội", "Nam Định", "Hải Phòng", "Quảng Ninh", "Đà Nẵng", "Huế", "Nghệ An", "Hà Tĩnh",
             "Thanh Hóa", "Bắc Ninh", "Lạng Sơn", "Sơn La", "Điện Biên", "Quảng Bình", "Quảng Trị", "Bình Định"]

def random_time_pair():
    departure = faker.date_time_between(start_date='+1d', end_date='+30d')
    arrival = departure + timedelta(hours=random.randint(2, 8))
    departure_str = departure.strftime("%H:%M %d-%m-%Y")
    arrival_str = arrival.strftime("%H:%M %d-%m-%Y")
    return departure_str, arrival_str

for i in range(1000):
    from_location, to_location = random.sample(locations, 2)
    departure_time, arrival_time = random_time_pair()
    price = random.randint(80000, 250000)
    total_seats = random.choice([28, 35, 40, 45])

    payload = {
        "busOperatorId": random.choice(bus_operator_ids),
        "fromLocation": from_location,
        "toLocation": to_location,
        "departurTime": departure_time,
        "arrivalTime": arrival_time,
        "price": price,
        "totalSeats": total_seats
    }

    response = requests.post(API_URL, json=payload, headers=headers)
    if response.status_code == 200:
        print(f"✅ Created trip {i+1}: {from_location} -> {to_location}")
    else:
        print(f"❌ Failed trip {i+1}: {response.status_code}, {response.text}")
