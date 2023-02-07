// React imports
import React, {useEffect, useState} from "react";
// Style imports
import "./App.css";
// Components imports
import SideBar from "./Components/SideBar/SideBar";
import FileUpload from "./Components/Fileupload/FileUpload";
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Offcanvas from 'react-bootstrap/Offcanvas';
// Services imports
// Types imports
// img imports
import logo from "./images/logo.png";

const App = () => {
    const [currentLessonId, setCurrentLessonId] = useState<number | undefined>();

    useEffect(() => {
        console.log(currentLessonId)
    },[currentLessonId])

    return (
        <>
            <Navbar bg="dark" variant="dark" expand={"md"}>
                <Container fluid>
                    <Navbar.Brand href="/">
                        <img
                            alt=""
                            src={logo}
                            width="30"
                            height="30"
                            className="d-inline-block align-top"
                        />{' '}
                        Les Doc
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls={`offcanvasNavbar-expand-${"md"}`}/>
                    <Navbar.Offcanvas
                        className="bg-dark d-md-none"
                        id={`offcanvasNavbar-expand-${"md"}`}
                        aria-labelledby={`offcanvasNavbarLabel-expand-${"md"}`}
                        placement="start"
                    >
                        <Offcanvas.Header closeButton closeVariant="white">
                            <Offcanvas.Title id={`offcanvasNavbarLabel-expand-${"md"}`} className="text-white">
                                Lessons
                            </Offcanvas.Title>
                        </Offcanvas.Header>
                        <Offcanvas.Body>
                            <SideBar setCurrentLessonId={setCurrentLessonId} currentLessonId={currentLessonId}/>
                        </Offcanvas.Body>
                    </Navbar.Offcanvas>
                </Container>
            </Navbar>
            <div className="d-flex flex-row h-100">
                <div className="d-none d-md-block" style={{width: "280px", height: "100%"}}>
                    <SideBar setCurrentLessonId={setCurrentLessonId} currentLessonId={currentLessonId}/>
                </div>
                <div className="flex-fill">
                    <div className="container py-3">
                        {currentLessonId == undefined ?
                            <div>
                                Select a lesson to view files
                            </div>
                            :
                            <FileUpload currentLessonId={currentLessonId}/>
                        }
                    </div>
                </div>
            </div>
        </>
    );
};

export default App;