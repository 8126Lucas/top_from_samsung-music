import { initializeApp } from "firebase/app";
import { getStorage, ref, getDownloadURL } from 'firebase/storage';

const positions = [
    '1️⃣', '2️⃣', '3️⃣', '4️⃣', '5️⃣', '6️⃣', '7️⃣', '8️⃣', '9️⃣',
    '1️⃣1️⃣', '1️⃣2️⃣', '1️⃣3️⃣', '1️⃣4️⃣', '1️⃣5️⃣', '1️⃣6️⃣', '1️⃣7️⃣', '1️⃣8️⃣', '1️⃣9️⃣',
    '2️⃣0️⃣', '2️⃣1️⃣', '2️⃣2️⃣', '2️⃣3️⃣', '2️⃣4️⃣', '2️⃣5️⃣', '2️⃣6️⃣', '2️⃣7️⃣', '2️⃣8️⃣', '2️⃣9️⃣',
    '3️⃣0️⃣', '3️⃣1️⃣', '3️⃣2️⃣', '3️⃣3️⃣', '3️⃣4️⃣', '3️⃣5️⃣', '3️⃣6️⃣', '3️⃣7️⃣', '3️⃣8️⃣', '3️⃣9️⃣',
    '4️⃣0️⃣', '4️⃣1️⃣', '4️⃣2️⃣', '4️⃣3️⃣', '4️⃣4️⃣', '4️⃣5️⃣', '4️⃣6️⃣', '4️⃣7️⃣', '4️⃣8️⃣', '4️⃣9️⃣',
    '5️⃣0️⃣', '5️⃣1️⃣', '5️⃣2️⃣', '5️⃣3️⃣', '5️⃣4️⃣', '5️⃣5️⃣', '5️⃣6️⃣', '5️⃣7️⃣', '5️⃣8️⃣', '5️⃣9️⃣',
    '6️⃣0️⃣', '6️⃣1️⃣', '6️⃣2️⃣', '6️⃣3️⃣', '6️⃣4️⃣', '6️⃣5️⃣', '6️⃣6️⃣', '6️⃣7️⃣', '6️⃣8️⃣', '6️⃣9️⃣',
    '7️⃣0️⃣', '7️⃣1️⃣', '7️⃣2️⃣', '7️⃣3️⃣', '7️⃣4️⃣', '7️⃣5️⃣', '7️⃣6️⃣', '7️⃣7️⃣', '7️⃣8️⃣', '7️⃣9️⃣',
    '8️⃣0️⃣', '8️⃣1️⃣', '8️⃣2️⃣', '8️⃣3️⃣', '8️⃣4️⃣', '8️⃣5️⃣', '8️⃣6️⃣', '8️⃣7️⃣', '8️⃣8️⃣', '8️⃣9️⃣',
    '9️⃣0️⃣', '9️⃣1️⃣', '9️⃣2️⃣', '9️⃣3️⃣', '9️⃣4️⃣', '9️⃣5️⃣', '9️⃣6️⃣', '9️⃣7️⃣', '9️⃣8️⃣', '9️⃣9️⃣',
    '1️⃣0️⃣0️⃣'
];


const firebaseConfig = {
  apiKey: "AIzaSyA1j9MLD-IMw-3ddcgpcUrlHiRIwn8c0HI",
  authDomain: "top-100-from-samsung-music.firebaseapp.com",
  projectId: "top-100-from-samsung-music",
  storageBucket: "top-100-from-samsung-music.firebasestorage.app",
  messagingSenderId: "338556604",
  appId: "1:338556604:web:6a7ebdd5960c51900102b9",
  measurementId: "G-YZ7HR572Y7"
};

const app = initializeApp(firebaseConfig);
const storage = getStorage();
const path_reference = ref(storage, "MOST_LISTENED.json");
getDownloadURL(path_reference)
    .then((url) => {
        return fetch(url)
    })
    .then((response) => {
        return response.json();
    })
    .then((json_data) => {
        console.log(json_data);
        displaySongs(json_data);
    });

var doc = document.body;

function displaySongs(json_data) {
    for(let i = 0; i < 100; i++) {
        if(json_data[i].album != "") {
            doc.innerHTML += `<div class="song">
            <div class="position">${i+1}</div>
            <div class="title">${json_data[i].title}</div>
            <div class="album">${json_data[i].album}</div>
            <div class="artist">${json_data[i].artist}</div>
            </div>`
        }
        else {
            doc.innerHTML += `<div class="song">
            <div class="position">${i+1}</div>
            <div class="title">${json_data[i].title}</div>
            <div class="artist">${json_data[i].artist}</div>
            </div>`
        }
    }
}
