import React from "react";
import Navbar from "react-bootstrap/Navbar";
import Container from "react-bootstrap/Container";
import logo from "../../images/logo.png";
import Offcanvas from "react-bootstrap/Offcanvas";
import SideBar from "../SideBar/SideBar";
import IMainLayout from "../../types/PropsTypes/MainLayoutPropsInterface";
import './MainLayout.css';

const MainLayout = (props: React.PropsWithChildren<IMainLayout>) => {

    return (
        <div className="d-flex flex-column h-100">
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
                            <SideBar/>
                        </Offcanvas.Body>
                    </Navbar.Offcanvas>
                </Container>
            </Navbar>
            <div className="sidebar__container d-flex flex-row">
                <div className="d-none d-md-block border-top border-secondary h-100" style={{width: "280px"}}>
                    <SideBar/>
                </div>
                <div className="flex-fill">
                    <div className="container py-3">
                        {props.children}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default MainLayout;