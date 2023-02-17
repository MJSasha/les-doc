import React, {useEffect, useState} from "react";
import CreateLessonForm from "../CreateLessonForm/CreateLessonForm";
import DeleteLessonAlert from "../DeleteLessonForm/DeleteLessonAlert";
import LessonItem from "../LessonItem/LessonItem";
import LessonService from "../../services/LessonService";
import ILesson from "../../types/LessonInterface";

const SideBar: React.FC = () => {
    const [lessonsList, setLessonsList] = useState<ILesson[]>([]);
    const [createLessonFormVisibility, setCreateLessonFormVisibility] = useState<boolean>(false);
    const [deleteLessonAlertVisibility, setDeleteLessonAlertVisibility] = useState<boolean>(false);
    const [currentDeletingLessonId, setCurrentDeletingLessonId] = useState<number | undefined>();


    useEffect(() => {
        UpdateLessonsList()
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
                <CreateLessonForm updateLessonsList={UpdateLessonsList} setVisible={setCreateLessonFormVisibility}
                                  visible={createLessonFormVisibility}/>
            }
            {deleteLessonAlertVisibility &&
                <DeleteLessonAlert updateLessonsList={UpdateLessonsList}
                                   currentDeletingLessonId={currentDeletingLessonId}
                                   setVisible={setDeleteLessonAlertVisibility} visible={deleteLessonAlertVisibility}/>
            }
            <div className="d-flex flex-column flex-shrink-0 text-white bg-dark pe-2"
                 style={{height: "100%"}}>
                <div className="px-3 pt-3">
                    <button type="button" className="btn btn-primary fs-5 w-100" onClick={() => {
                        setCreateLessonFormVisibility(!createLessonFormVisibility)
                    }}>New
                    </button>
                </div>
                <hr/>
                <ul className="nav nav-pills mb-auto overflow-auto">
                    {lessonsList &&
                        lessonsList.map((lesson, index) => {
                            return <li key={index} className="nav-item mb-2 w-100 px-3">
                                <LessonItem
                                    lesson={lesson}
                                    setDeleteLessonAlertVisibility={setDeleteLessonAlertVisibility}
                                    deleteLessonAlertVisibility={deleteLessonAlertVisibility}
                                    setCurrentDeletingLessonId={setCurrentDeletingLessonId}
                                />
                            </li>
                        })
                    }
                </ul>
            </div>
        </>
    )
}

export default SideBar;