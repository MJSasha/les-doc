import React, {useState} from "react";
import FileService from "../../services/FileService";
import {useParams} from "react-router-dom";
import IFileUpload from "../../types/PropsTypes/FileUploadPropsInterface";

const FileUpload: React.FC<IFileUpload> = ({updateFileList, setVisible}) => {
    const [currentFile, setCurrentFile] = useState<File | undefined>();
    const [progress, setProgress] = useState<number>(0);
    const [message, setMessage] = useState<string>("");

    const {currentLessonId} = useParams();
    const id = Number(currentLessonId);


    const selectFile = (event: React.ChangeEvent<HTMLInputElement>) => {
        const {files} = event.target;
        const selectedFile = files as FileList;
        setCurrentFile(selectedFile?.[0]);
        setProgress(0);
    };

    const upload = () => {
        setProgress(0);
        if (!currentFile) return;
        FileService.upload(id, currentFile, (event: any) => {
            setProgress(Math.round((100 * event.loaded) / event.total));
            // setTimeout(() => {
            //     setVisible(false)
            // }, 1000)
        })
            .then((res) => {
                setMessage(res.data.message);
                updateFileList();
                // setVisible(false);
            })
            .catch((err) => {
                setProgress(0);
                if (err.response && err.response.data && err.response.data.message) {
                    setMessage(err.response.data.message);
                } else {
                    setMessage("Could not upload the File!");
                }
                setCurrentFile(undefined);
            });
    };

    return (
        <>
            <div className="modal-header container d-flex flex-row justify-content-between pt-2 px-3">
                <h5 className="modal-title">Upload file</h5>
                <button onClick={() => {
                    setVisible(false)
                }} type="button" className="btn-close"
                        aria-label="Закрыть"></button>
            </div>
            <div className="modal-body">
                <div className="d-flex flex-column justify-content-center h-100 p-3">
                    <label className="btn btn-default p-0">
                        <input type="file" onChange={selectFile}/>
                    </label>
                    {currentFile && (
                        <div className="progress h-100 mt-2" style={{minHeight: '12px'}}>
                            <div
                                className="progress-bar progress-bar-striped progress-bar-animated"
                                role="progressbar"
                                aria-valuenow={progress}
                                aria-valuemin={0}
                                aria-valuemax={100}
                                style={{width: progress + "%"}}
                            >
                                {progress}%
                            </div>
                        </div>
                    )}
                    {message && (
                        <div className="text-danger" role="alert">
                            {message}
                        </div>
                    )}
                </div>
            </div>
            <div className="modal-footer">
                <div className="pb-2 px-3">
                    <button
                        className="btn btn-success btn-sm"
                        disabled={!currentFile}
                        onClick={() => {
                            upload();
                        }}
                    >
                        Upload
                    </button>
                </div>
            </div>


        </>
    );
};

export default FileUpload;