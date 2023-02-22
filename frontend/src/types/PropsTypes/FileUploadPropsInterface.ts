import {Dispatch, SetStateAction} from "react";

export default interface IFileUpload {
    updateFileList: () => void,
    setVisible: Dispatch<SetStateAction<boolean>>
}