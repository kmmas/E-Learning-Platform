import './HomePage.css'

export const HomePage = () => {
    return (
        <>
            <div class="allg">
                <div class="allitems">
                    <div class="content">
                        <div class="game"></div>
                        <div class="body">
                            <h1>E-LEARNING PLATFORM</h1>
                            <p id='intoduction'>Welcome in our ELearning website </p>
                            <section>Your success guide is here</section>
                            <ul class="info">
                                <li><a href="/">Contact us</a> </li>
                                <li><a href="#section1">Read more</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="sl">
                        <div class="presentation">
                            <div class="speech" id="section1">
                                <h1>Why Elearning platform</h1>
                                <p className='speachtxt'>Instead of spending a long time looking for 
                                    mathematical information, this site provides you 
                                    with everything you need in mathematics for all 
                                    ages and forms a basic infrastructure for you that 
                                    facilitates your scientific and human dealings.Our mission is to empower learners with the skills and knowledge they need to thrive in a rapidly evolving world. Through our eLearning platform, we aim to break down barriers to education, making learning a flexible and enriching journey for everyone.</p>
                                    <div><a href="/profile" style={{textDecoration:"none", color:"white"}} class="start">Your Profile</a></div>
                            </div>
                            <div class="img2"></div>
                        </div>
                        <div class="learn">
                            <div class="learn-img"></div>
                            <div class="learn-txt">
                                <h1>COURSES</h1>
                                <p className='speachtxt'>an intellectual playground where learning takes center stage! At [Platform Name], we are thrilled to present a diverse and enriching collection of courses designed to spark curiosity, fuel passion, and drive personal and professional growth..</p>
                                
                            </div>
                        </div>
                        <div class="grad">
                            <div class="grad-txt">
                                <h1>Top Students</h1>
                                <p className='speachtxt'>Every year, many top achievers emerge from our society at the level of the Republic. Therefore, we are working to build a constructive scientific generation capable of achieving the impossible..</p>
                                <div class="startgrad"><a href="#services" id="top-students" >Get started</a></div>
                            </div>
                            <div class="grad-img"></div>
                        </div>
                    </div>
                    <header class="members">Content</header>
                    <div class="overview" id="services">
                        <div class="cont3" >
                            <div class="d">
                                <div class="topStudents"></div>
                                <h2>Profile page</h2>
                                <p>Students how are registered will have profile showing his/her achivements</p>
                            </div>
                            <div class="d">
                                <div class="videos"></div>
                                <h2>COURSES</h2>
                                <p>The most important reason for enrolling to boost your knowledge</p>
                            </div>
                            <div class="d">
                                <div class="laws"></div>
                                <h2>ANOUNCEMENTS</h2>
                                <p>Important anouncements and decisions from Instructors can be broadcasted to his/her students</p>
                            </div>
                            <div class="d">
                                <div class="centers"></div>
                                <h2>MAKE COURSE</h2>
                                <p>Instructors can register to add his own courses for students</p>
                            </div>
                        </div>
                    </div>
                    <div class="big-footer">
                        <div class="footerlists pt-4">
                            <div class="p">
                                <div class="head">elearning platform</div>
                                <ul>
                                    <li><a href="/">Terms</a> </li>
                                    <li><a href="/">Licenses</a> </li>
                                    <li><a href="/">Market API</a> </li>
                                    <li><a href="/">Become an Engineer</a> </li>
                                </ul>
                            </div>
                            <div class="p">
                                <div class="head">HELP</div>
                                <ul>
                                    <li><a href="/">Help Center</a> </li>
                                    <li><a href="/">Authors</a> </li>
                                </ul>
                            </div>
                            <div class="p">
                                <div class="head">OUR COMMUNITY</div>
                                <ul>
                                    <li><a href="/">Community</a> </li>
                                    <li><a href="/">Blog</a> </li>
                                    <li><a href="/">Forums</a> </li>
                                    <li><a href="/">Meetups</a> </li>
                                </ul>
                            </div>
                            <div class="p last">
                                <div class="head ">MEET MATH</div>
                                <ul>
                                    <li><a href="/">About Math</a> </li>
                                    <li><a href="/">Careers</a> </li>
                                    <li><a href="/">Privacy Plicy</a> </li>
                                    <li><a href="/">Sitmap</a> </li>
                                </ul>
                            </div>
                        </div>
                        <div class="copyright">Copyright &copy; 2023-2024 OnlineLearning.com website. MADE BY SWE Team Directed by ENG.Elnaggar</div>
                    </div>
                </div>
            </div>
        </>
    )
}
export default HomePage;
