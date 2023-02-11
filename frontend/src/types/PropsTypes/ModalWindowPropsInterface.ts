import {Dispatch, SetStateAction} from "react";

export default interface IModalContent{
    getModalContent: () => JSX.Element,
    setVisible: Dispatch<SetStateAction<boolean>>,
    visible: boolean
}