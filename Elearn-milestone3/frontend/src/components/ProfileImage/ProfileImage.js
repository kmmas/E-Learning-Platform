import "./ProfileImage.css"
import { Link } from 'react-router-dom';
import { useState, useEffect } from "react"
import { useAuth, upload } from "../../firebase"
const ProfileImage = (props) => {
    const Options = (props) => {
        const page = props.page
        if (page == 2) {
            return (<div className="button"><Link to="/makecourse" className="btn btn-dark ">Create Course</Link></div>)
        } else if (page == 1) {
            return (<div className="button"><Link to="/courseenroll" className="btn btn-dark ">View Courses</Link></div>)
        } else {
            return (
                <>
                    <div className="button"><Link to="/makecourse" className="btn btn-dark ">Create Course</Link></div>
                    <div className="button"><Link to="/admin" className="btn btn-dark ">go to admin page</Link></div>
                </>
            )
        }
    }
    const currentUser = useAuth();
    const [loading, setLoading] = useState(false);
    const [photoURL, setPhotoURL] = useState("https://cdn.vectorstock.com/i/preview-1x/51/05/male-profile-avatar-with-brown-hair-vector-12055105.webp");

    function handleBrowse(event) {
        upload(event.target.files[0], currentUser, setLoading, setPhotoURL);
    }

    useEffect(() => {
        if (currentUser?.photoURL) {
            setPhotoURL(currentUser.photoURL)
        }
        console.log("hdhh" + props.page)
    }, [currentUser])



    return (
        <>
            <div className="all-profile">
                <div className="image-container">
                    <div className="profile">
                        <img id="profile" src={photoURL} alt='profile' className='profile-img' />
                        <input type="file" id="file-input" name="file-input" onChange={handleBrowse} />
                        <label disabled={loading} id="file-input-label" for="file-input">Edit profile picture</label>
                        <h2>{props.name}</h2>
                        <span className="user-status">{props.position}</span>
                        <span className="user-status">{props.role}</span>
                        <span className="user-about">Everyone has the right to an effective remedy by the competent national tribunals for acts violating the fundamental rights </span>
                    </div>
                    <div><Link to="/updateprofile" className="btn btn-dark edit-profile">Edit Profile</Link></div>
                </div>
                <div className="intro">
                    <h1 id="header1">ONLINE LEARNING PLATFORM</h1>
                    <h3 id="header2">Boost your knowledge</h3>
                    <span className="intro-discription">Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sunt recusandae ea dolorem nemo perspiciatis quasi mollitia fugiat quae ullam neque voluptatum illo unde, facere accusamus provident quos et quidem dignissimos, assumenda dolore nisi. Natus nemo perspiciatis facilis hic, quia accusantium totam nesciunt! Ab deleniti animi sint natus temporibus impedit qui sapiente eius.</span>
                    <div className="buttons">
                        <Options page={props.page} />
                    </div>
                </div>
            </div>
        </>
    )
}
export default ProfileImage;