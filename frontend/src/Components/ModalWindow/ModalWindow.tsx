import React, {useEffect, useRef} from "react";
import {useOnClickOutside} from 'usehooks-ts'

import IModalContent from "../../types/PropsTypes/ModalWindowPropsInterface";

import './ModalWindow.css'

const ModalWindow: React.FC<IModalContent> = ({getModalContent, setVisible, visible}) => {
    const modalRef = useRef(null);

    useOnClickOutside(modalRef, () => setVisible(false));

    useEffect(() => {
        const close = (e: KeyboardEvent) => {
            if (e.key === 'Escape') {
                setVisible(false)
            }
        }
        window.addEventListener('keydown', close)
        return () => window.removeEventListener('keydown', close)
    }, [])

    return (
        <>
            {visible ?
                <div className="modal__wrapper">
                    <div className="modal-dialog modal-dialog-centered modal_content_wrapper_zone">
                        <div ref={modalRef}
                             className="modal-content modal_content_zone d-flex flex-column justify-content-between">
                            {getModalContent}
                        </div>
                    </div>
                </div>
                :
                null
        }
        </>
    )
}

export default ModalWindow;