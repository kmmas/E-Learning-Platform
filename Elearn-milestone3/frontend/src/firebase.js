// Import the functions you need from the SDKs you need
import { useEffect, useState } from "react";
import { initializeApp } from "firebase/app";
import { getDownloadURL, getStorage, ref, uploadBytes } from "firebase/storage"
import {getAuth, createUserWithEmailAndPassword,onAuthStateChanged, signOut,signInWithEmailAndPassword, updateProfile} from "firebase/auth"
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
    apiKey: "AIzaSyDs8lDI4iOdH1_87wZ7RpcqAAocW2P7hjA",
    authDomain: "profile-3a91f.firebaseapp.com",
    projectId: "profile-3a91f",
    storageBucket: "profile-3a91f.appspot.com",
    messagingSenderId: "750881172796",
    appId: "1:750881172796:web:fa001b473a1692bf9c0239",
    measurementId: "G-41VMJYERJX"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const storage = getStorage(app);

const auth = getAuth();
export function signup(email,password){
    return createUserWithEmailAndPassword(auth,email,password);
}
export function login(email,password){
    return signInWithEmailAndPassword(auth,email,password);
}

export function logout(){
    return signOut(auth);
}

export function useAuth(){
    const [currentUser, setCurrentUser] = useState();
    useEffect(()=>{
        const listen = onAuthStateChanged(auth, user => setCurrentUser(user))
        return listen;
    },[])
    return currentUser;
}

export async function upload(file,currentUser,setLoading,setPhotoURL){
    const fileRef = ref(storage,`images/${currentUser.uid}.png`);
    setLoading(true);
    await uploadBytes(fileRef,file).then((snapshot)=>{
        getDownloadURL(snapshot.ref).then((photoURL)=>{
            updateProfile(currentUser,{photoURL})
            setPhotoURL(photoURL)
        })
    });
    setLoading(false);
}