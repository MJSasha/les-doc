import React, {useEffect, useState} from "react";
import FileService from "../../services/FileService";
import {useParams} from "react-router-dom";
import ModalWindow from "../ModalWindow/ModalWindow";
import FileUpload from "../Fileupload/FileUpload";
import delete_icon from "../../images/delete_icon.png";
import download_icon from "../../images/download_icon.png";

const FileList: React.FC = () => {
    const [fileList, setFileList] = useState<string[]>(['']);
    const [errMsg, setErrMsg] = useState<string>('There are no files here');
    const [uploadFileFormVisibility, setUploadFileFormVisibility] = useState<boolean>(false);

    const {currentLessonId} = useParams();
    const id = Number(currentLessonId);

    useEffect(() => {
        getAllFiles()
    }, [id])

    const getAllFiles = () => {
        FileService.getAll(id)
            .then((res) => {
                setFileList(res.data)
                setErrMsg('')
            })
            .catch(() => {
                setErrMsg('There are no files here')
                setFileList([''])
            })
    }

    const deleteFile = (fileName: string) => {
        FileService.del(id, fileName)
            .then(res => {
                console.log(res)
                getAllFiles()
            })
            .catch(err => {
                console.log(err)
            })
    }

    const downloadFile = (fileName: string) => {
        FileService.download(id, fileName)
            .then(res => {
                const url = window.URL.createObjectURL(new Blob([res.data]))
                const link = document.createElement('a')
                link.href = url
                link.setAttribute('download', fileName)
                document.body.appendChild(link)
                link.click()
            })
            .catch(err => {
                console.log(err)
            })
    }

    return (
        <div>
            <ModalWindow
                getModalContent={<FileUpload updateFileList={getAllFiles} setVisible={setUploadFileFormVisibility}/>}
                setVisible={setUploadFileFormVisibility} visible={uploadFileFormVisibility}/>
            <button type="button" className="btn btn-primary fs-5 w-100 w-25" onClick={() => {
                setUploadFileFormVisibility(true)
            }}>Add file
            </button>
            {errMsg ? errMsg :
                <div>
                    {fileList && fileList.map((file, index) => {
                        return (
                            <div key={index} className="border border-primary m-1 h-100 w-100">
                                {file}
                                <button className="btn btn-sm" onClick={() => {
                                    deleteFile(file)
                                }}>
                                    <img src={delete_icon} alt={delete_icon}
                                         style={{width: "1.1rem", filter: "invert(0.5)"}}/>
                                </button>
                                <button className="btn btn-sm" onClick={() => {
                                    downloadFile(file)
                                }}>
                                    <img src={download_icon} alt={download_icon}
                                         style={{width: "1.1rem", filter: "invert(0.4)"}}/>
                                </button>
                            </div>
                        )
                    })
                    }
                </div>
            }
        </div>
    )
}

export default FileList;