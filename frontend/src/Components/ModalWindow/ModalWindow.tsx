import React, {useRef} from "react";
import {useOnClickOutside} from 'usehooks-ts'

import IModalContent from "../../types/ModalWindowPropsInterface";

import './ModalWindow.css'

const ModalWindow: React.FC<IModalContent> = ({GetModalContent, setVisible, visible}) => {
    const modalRef = useRef(null);

    useOnClickOutside(modalRef, () => setVisible(false));
    return (
        <div className="modal__wrapper">
            <div className="modal-dialog modal-dialog-centered">
                <div ref={modalRef} className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Заголовок модального окна</h5>
                        <button onClick={() => {
                            setVisible(false)
                        }} type="button" className="btn-close"
                                aria-label="Закрыть"></button>
                    </div>
                    <div className="modal-body">
                        {GetModalContent()}
                    </div>
                    <div className="modal-footer">

                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalWindow;