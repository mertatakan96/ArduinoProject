#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>

#define WIFI_SSID "" //WiFi Name
#define WIFI_PASSWORD ""  // WiFi Password
#define FIREBASE_HOST "arduinoproject-3c1f9-default-rtdb.europe-west1.firebasedatabase.app"
#define FIREBASE_AUTH "ES3oHRTJFZxNEiyMu1vG1RlSOoh8lkKwtRNgMar5" 

int data;
String value;

FirebaseData database;

void setup() {

  Serial.begin(115200);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Ağ Bağlantısı Oluşturuluyor");
  while(WiFi.status()!= WL_CONNECTED){
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("IP adresine bağlanıldı: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH); // Firebase bağlantısı
  
  Firebase.reconnectWiFi(true); //Ağ bağlantısı kesilirse

  data = analogRead(A0);
  value = String(data, DEC);
  

  Serial.println("Analog Value: ");
  Serial.println(value);
  delay(500);

  if(Firebase.setString(database, "/user/data", value)){
    Serial.println("Veri gönderimi başarılı!");
  }else{
    Serial.println("Gönderilemedi " + database.errorReason());
  }

  Serial.println("1 saat uyu");
  delay(1000);

  ESP.deepSleep(36e8); //sleeping 1 hour //20e6 for 20 seconds
  delay(20);
  
  
}

void loop() {
}
