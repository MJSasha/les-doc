import React, {useState} from "react";
import delete_icon from "../../images/delete_icon.png";
import './LessonItem.css';
import ILessonItem from "../../types/PropsTypes/LessonItemPropsInterface";
import {useNavigate, useParams} from "react-router-dom";

const LessonItem: React.FC<ILessonItem> = ({
                                               lesson,
                                               setDeleteLessonAlertVisibility,
                                               deleteLessonAlertVisibility,
                                               setCurrentDeletingLessonId
                                           }) => {
    const [hover, setHover] = useState<boolean>(false);
    const navigate = useNavigate();
    const {currentLessonId} = useParams();
    const id = Number(currentLessonId);

    const DeleteButton = () => {
        return (
            <>
                <button className="btn btn-sm" onClick={() => {
                    setDeleteLessonAlertVisibility(!deleteLessonAlertVisibility)
                    setCurrentDeletingLessonId(lesson.id)
                }}>
                    <img src={delete_icon} alt={delete_icon}
                         style={{width: "1.1rem", filter: "invert(1)"}}/>
                </button>
            </>
        )
    }

    return (
        <>
            <a
                tabIndex={0}
                onMouseEnter={() => setHover(true)}
                onMouseLeave={() => setHover(false)}
                onClick={() => {
                    navigate(`/lesson/${lesson.id}`)
                }}
                className={`nav-link text-white border border-primary d-flex justify-content-between align-items-center custom__hover text-break ${lesson.id === id ? 'custom__active' : ''}`}>
                {lesson.name}
                <div className="d-none d-md-block" style={{width: "36px", height: "32px"}}>
                    {hover &&
                        DeleteButton()
                    }
                </div>
                <div className="d-block d-md-none">
                    {
                        DeleteButton()
                    }
                </div>
            </a>
        </>
    )
}

export default LessonItem;