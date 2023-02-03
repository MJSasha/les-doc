import React, {useRef} from "react";
import {useOnClickOutside} from 'usehooks-ts'

import IModalContent from "../../types/PropsTypes/ModalWindowPropsInterface";

import './ModalWindow.css'

const ModalWindow: React.FC<IModalContent> = ({getModalContent, setVisible, visible}) => {
    const modalRef = useRef(null);

    useOnClickOutside(modalRef, () => setVisible(false));
    return (
        <div className="modal__wrapper">
            <div className="modal-dialog modal-dialog-centered modal_content_wrapper_zone">
                <div ref={modalRef}
                     className="modal-content modal_content_zone d-flex flex-column justify-content-between">
                    <div className="modal-header d-flex flex-row justify-content-end pt-2 pe-2">
                        {/*<h5 className="modal-title">Заголовок модального окна</h5>*/}
                        <button onClick={() => {
                            setVisible(false)
                        }} type="button" className="btn-close"
                                aria-label="Закрыть"></button>
                    </div>
                    <div className="modal-body">
                        {getModalContent()}
                    </div>
                    {/*<div className="modal-footer">*/}

                    {/*</div>*/}
                </div>
            </div>
        </div>
    )
}

export default ModalWindow;