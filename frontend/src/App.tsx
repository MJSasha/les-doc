import React from "react";
import {HashRouter, Route, Routes} from "react-router-dom";
import FileList from "./Components/FileList/FileList";
import MainLayout from "./Components/MainLayout/MainLayout";
import FileUpload from "./Components/Fileupload/FileUpload";

const App = () => {

    return (
        <HashRouter>
            <Routes>
                <Route path="/" element={<MainLayout/>}/>
                <Route path="/lesson/:currentLessonId" element={<MainLayout><FileUpload/><FileList/></MainLayout>}/>
            </Routes>
        </HashRouter>
    );
};

export default App;