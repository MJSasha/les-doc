import React, {Dispatch, SetStateAction} from "react";

export default interface IModalContent{
    getModalContent: React.ReactElement,
    setVisible: Dispatch<SetStateAction<boolean>>,
    visible: boolean
}