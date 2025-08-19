import { initializeApp } from "firebase/app";
import { getStorage, ref, getDownloadURL } from 'firebase/storage';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger'; 

const firebaseConfig = {
  apiKey: "AIzaSyA1j9MLD-IMw-3ddcgpcUrlHiRIwn8c0HI",
  authDomain: "top-100-from-samsung-music.firebaseapp.com",
  projectId: "top-100-from-samsung-music",
  storageBucket: "top-100-from-samsung-music.firebasestorage.app",
  messagingSenderId: "338556604",
  appId: "1:338556604:web:6a7ebdd5960c51900102b9",
  measurementId: "G-YZ7HR572Y7"
};

gsap.registerPlugin(ScrollTrigger);

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
            duration: 0.8,
            stagger: 0.3,
            ease: "back.out",
        });
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

