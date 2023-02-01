// React imports
import React, {useState, useEffect, Dispatch, SetStateAction} from "react";
// Style imports
import "./App.css";
// Components imports
import FileUpload from "./Components/Fileupload/FileUpload";
import CreateLesson from "./Components/CreateLesson/CreateLesson";
// Services imports
import LessonService from "./services/LessonService";
// Types imports
import ILesson from "./types/LessonInterface";

const App = () => {
    const [lessonsList, setLessonsList] = useState<ILesson[]>([]);

    useEffect(() => {
        LessonService.GetAll()
            .then(res => {
                    setLessonsList(res.data)
                }
            )
            .catch(err => {
                console.log(err)
            })
    },[])

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
            <div className="d-flex flex-column flex-shrink-0 p-3 text-white bg-dark"
                 style={{width: "280px", height: "100%"}}>
                <a href="/"
                   className="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
                    <span className="fs-4">LesDoc</span>
                </a>
                <hr/>
                <CreateLesson UpdateLessonList={UpdateLessonsList}/>
                <hr/>
                <ul className="nav nav-pills flex-column mb-auto">
                    {lessonsList &&
                        lessonsList.map((lesson, index) => {
                            return <li key={index} className="nav-item mb-2">
                                <a href="#" className="nav-link text-white active">{lesson.name}</a>
                                <button onClick={() => {
                                    LessonService.Delete(lesson.id)
                                        .then(() => {
                                                UpdateLessonsList()
                                            }
                                        )
                                        .catch(err => {
                                            console.log(err)
                                        })
                                }}>x
                                </button>
                            </li>
                        })
                    }
                </ul>
            </div>
        </>
    );
};

export default App;