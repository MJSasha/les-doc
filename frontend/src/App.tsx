// React imports
import React from "react";
// Style imports
import "./App.css";
// Components imports
import FileUpload from "./Components/Fileupload/FileUpload";
import CreateLesson from "./Components/CreateLesson/CreateLesson";
// Services imports
import GetAllLessonsService from "./services/GetAllLessonsService";

const App = () => {

    return (
        <>
            <div className="d-flex flex-column flex-shrink-0 p-3 text-white bg-dark"
                 style={{width: "280px", height: "100%"}}>
                <a href="/"
                   className="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
                    <span className="fs-4">LesDoc</span>
                </a>
                <hr/>
                <CreateLesson/>
                <hr/>
                <ul className="nav nav-pills flex-column mb-auto">
                    <li className="nav-item">
                        <a href="#" className="nav-link active" aria-current="page">
                            Home
                        </a>
                    </li>
                    <li>
                        <a href="#" className="nav-link text-white">
                            Dashboard
                        </a>
                    </li>
                    <li>
                        <a href="#" className="nav-link text-white">
                            Orders
                        </a>
                    </li>
                    <li>
                        <a href="#" className="nav-link text-white">
                            Products
                        </a>
                    </li>
                    <li>
                        <a href="#" className="nav-link text-white">
                            Customers
                        </a>
                    </li>
                </ul>
            </div>
        </>
    );
};

export default App;