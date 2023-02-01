import {Dispatch, SetStateAction} from "react";

export default interface IModalContent{
    GetModalContent: () => JSX.Element,
    setVisible: Dispatch<SetStateAction<boolean>>,
    visible: boolean
}