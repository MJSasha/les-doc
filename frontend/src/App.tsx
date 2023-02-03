// React imports
import React, {useEffect, useState} from "react";
// Style imports
import "./App.css";
// Components imports
import CreateLessonForm from "./Components/CreateLessonForm/CreateLessonForm";
import DeleteLessonAlert from "./Components/DeleteLessonForm/DeleteLessonAlert";
// Services imports
import LessonService from "./services/LessonService";
// Types imports
import ILesson from "./types/LessonInterface";
// img imports
import logo from "./images/logo.png";
import delete_icon from "./images/delete_icon.png";

const App = () => {
    const [lessonsList, setLessonsList] = useState<ILesson[]>([]);
    const [createLessonFormVisibility, setCreateLessonFormVisibility] = useState<boolean>(false);
    const [deleteLessonAlertVisibility, setDeleteLessonAlertVisibility] = useState<boolean>(false);
    const [currentDeletingLessonId, setCurrentDeletingLessonId] = useState<number | undefined>();

    useEffect(() => {
        LessonService.GetAll()
            .then(res => {
                    setLessonsList(res.data)
                }
            )
            .catch(err => {
                console.log(err)
            })
    }, [])

    const UpdateLessonsList = () => {
        LessonService.GetAll()
            .then(res => {
                    setLessonsList(res.data)
                }
            )
            .catch(err => {
                console.log(err)
            })
    }


    return (
        <>
            {createLessonFormVisibility &&
                <CreateLessonForm UpdateLessonsList={UpdateLessonsList} setVisible={setCreateLessonFormVisibility}
                                  visible={createLessonFormVisibility}/>
            }
            {deleteLessonAlertVisibility &&
                <DeleteLessonAlert UpdateLessonsList={UpdateLessonsList}
                                   currentDeletingLessonId={currentDeletingLessonId}
                                   setVisible={setDeleteLessonAlertVisibility} visible={deleteLessonAlertVisibility}/>
            }
            <div className="d-flex flex-column flex-shrink-0 p-3 text-white bg-dark"
                 style={{width: "280px", height: "100%"}}>
                <a href="/"
                   className="d-flex flex-row justify-content-between align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
                    <img className="col-6" src={logo} alt={logo} style={{width: "2.5rem"}}/>
                    <span className="fs-4 col-6">LesDoc</span>
                </a>
                <hr/>
                <button type="button" className="btn btn-primary create_lesson_btn fs-5" onClick={() => {
                    setCreateLessonFormVisibility(!createLessonFormVisibility)
                }}>New
                </button>
                <hr/>
                <ul className="nav nav-pills flex-column mb-auto">
                    {lessonsList &&
                        lessonsList.map((lesson, index) => {
                            return <li key={index} className="nav-item mb-2">
                                <a href="#"
                                   className="nav-link text-white active d-flex justify-content-between align-items-center fs-5">
                                    {lesson.name}
                                    <button className="btn btn-sm" onClick={() => {
                                        setDeleteLessonAlertVisibility(!deleteLessonAlertVisibility)
                                        setCurrentDeletingLessonId(lesson.id)
                                    }}>
                                        <img src={delete_icon} alt={delete_icon}
                                             style={{width: "1.1rem", filter: "invert(1)"}}/>
                                    </button>
                                </a>
                            </li>
                        })
                    }
                </ul>
            </div>
        </>
    );
};

export default App;