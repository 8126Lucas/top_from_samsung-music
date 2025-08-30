import { initializeApp } from "firebase/app";
import { getStorage, ref, getDownloadURL, getMetadata } from 'firebase/storage';
import { gsap } from 'gsap';

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
        displaySongs(json_data);
        gsap.from(".song", {
            opacity: 0,
            scale: 0.8,
            y: 50,
            duration: 0.5,
            stagger: 0.1,
            ease: "back.out(1.7)",
            rotate: 5,
            transformOrigin: "center bottom",
        });
    });
getMetadata(path_reference)
    .then((metadata) => {
        lastUpdated(metadata.timeCreated);
    })
    .catch(() => {
        lastUpdated("???");
    });

function displaySongs(json_data) {
    var doc = document.body;
    for(let i = 0; i < 100; i++) {
        if(json_data[i].album != "") {
            doc.innerHTML += `<div class="song">
            <div class="position">#${i+1}</div>
            <div class="title">${json_data[i].title}</div>
            <div class="album">${json_data[i].album}</div>
            <div class="artist">${json_data[i].artist}</div>
            <div class="duration">${durationTimestamp(parseInt(json_data[i].duration))}</div>
            <div class="youtube"><a href="${json_data[i].youtube}"><img src="src/youtube.svg"></a></div>
            </div>`
        }
        else {
            doc.innerHTML += `<div class="song">
            <div class="position">#${i+1}</div>
            <div class="title">${json_data[i].title}</div>
            <div class="artist">${json_data[i].artist}</div>
            <div class="duration">${durationTimestamp(parseInt(json_data[i].duration))}</div>
            <div class="youtube"><a href="${json_data[i].youtube}"><img src="src/youtube.svg"></a></div>
            </div>`
        }
    }
}

function durationTimestamp(duration) {
    let minutes = Math.floor(duration / 60);
    let seconds = duration % 60;
    let time = minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0');
    return time;
}

function lastUpdated(time_updated) {
    var doc = document.body;
    const date = new Date(time_updated);
    const date_formatted = `${date.getDate().toString().padStart(2, '0')}/${(date.getMonth() + 1).toString().padStart(2, '0')}/${date.getFullYear()}`;
    doc.innerHTML += `<div class="updated">Last updated: ${date_formatted}</div>`;
} 