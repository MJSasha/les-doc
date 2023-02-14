import React, {useState} from "react";
import delete_icon from "../../images/delete_icon.png";

import ILessonItem from "../../types/PropsTypes/LessonItemPropsInterface";

const LessonItem: React.FC<ILessonItem> = ({
                                               lesson,
                                               setDeleteLessonAlertVisibility,
                                               deleteLessonAlertVisibility,
                                               setCurrentDeletingLessonId
                                           }) => {
    const [hover, setHover] = useState<boolean>(false)

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
                className="nav-link text-white border border-primary d-flex justify-content-between align-items-center custom__hover text-break">
                <div className="col-10 my-1">
                    {lesson.name}
                </div>
                <div className="d-none d-md-block col-2">
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