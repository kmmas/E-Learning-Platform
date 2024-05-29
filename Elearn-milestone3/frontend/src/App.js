import './App.css';
import { ElearningPage, Header, CourseEnroll, MakeCourse, Signup, Login, StudentProfile, Course, MakeLecture, HomePage, MakeAnnounm, NotFound } from "./components/index"
import { UpdateProfile } from "./components/index"
import { Admin } from "./components/index"
import { BrowserRouter, Route, Routes } from "react-router-dom"
import { CookiesProvider, useCookies } from "react-cookie";
import React, { useState } from 'react';

function App() {
  const [cookies, setCookie] = useCookies(["user"]);
  function handleLogin(user) {
    setCookie("user", user, { path: "/" });
  }
  const [course, setCookie2] = useCookies(["course"]);
  function handlecourse(course) {
    setCookie2("course", course, { path: "/" });
  }
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route
            path="/elearning"
            element={
              <>
                <Header />
                <HomePage />
              </>
            }
          />
          <Route path="/course" element={<Course user={cookies.user} course = {course.course}  />} />
          <Route path="/courseenroll" element={<CourseEnroll user={cookies.user} />} />
          <Route path="/makecourse" element={<MakeCourse user={cookies.user}  />} />
          <Route path="/makelecture" element={<MakeLecture user={cookies.user}  course = {course.course} />} />
          <Route path="/makeannoun" element={<MakeAnnounm user={cookies.user} course = {course.course} />} />
          <Route path="*" element={<NotFound user={cookies.user} />} />
          <Route path="/admin" element={<><Admin user={cookies.user} /></>} /> 
          <Route index element={
            <CookiesProvider>

              <div>
                {cookies.user || !cookies.user ? (
                  <Login onLogin={handleLogin} />
                ) : (
                  <></>
                )}
              </div>
            </CookiesProvider>
          }
          />
          <Route path="/signup" element={<Signup />} />
          <Route
            path="/updateprofile"
            element={
              <>
                <Header />
                <UpdateProfile user={cookies.user} />
              </>
            }
          />
          <Route
            path="/profile"
            element={
              <ElearningPage>
                <Header />
                <StudentProfile user={cookies.user} courseContent= {handlecourse} />
              </ElearningPage>
            }
          />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
