import React, {useState} from "react";
import FileService from "../../services/FileService";
import IFile from "../../types/FileInterface";
import IFileUpload from "../../types/PropsTypes/FileUploadPropsInterface";

const FileUpload: React.FC<IFileUpload> = ({currentLessonId}) => {
    const [currentFile, setCurrentFile] = useState<File | undefined>();
    const [progress, setProgress] = useState<number>(0);
    const [message, setMessage] = useState<string>("");
    const [fileInfos, setFileInfos] = useState<File[]>([]);

    // useEffect(() => {
    //     UploadService.getAllFilesNames(currentLessonId).then((res) => {
    //         console.log(res)
    //         setFileInfos(res.data);
    //         // console.log(fileInfos)
    //     });
    // },[message]);

    const getAllFileNames = () => {
        FileService.getAllFilesNames(currentLessonId)
            .then((res) => {
                console.log(res)
                setFileInfos(res.data);
                // console.log(fileInfos)
            })
            .catch(err => {
                    console.log(err)
                })
    }

    // const selectFile = (event: React.ChangeEvent<HTMLInputElement>) => {
    //     const {files} = event.target;
    //     const selectedFile = files as FileList;
    //     setCurrentFile(selectedFile?.[0]);
    //     setProgress(0);
    // };
    const selectFile = (event: React.ChangeEvent<HTMLInputElement>) => {
        const {files} = event.target;
        const selectedFile = files as FileList;
        setCurrentFile(selectedFile?.[0]);
        setProgress(0);
    };

    const upload = () => {
        setProgress(0);
        if (!currentFile) return;
        console.log(currentFile)
        FileService.upload(currentLessonId, currentFile, (event: any) => {
            setProgress(Math.round((100 * event.loaded) / event.total));
        })
            .then((res) => {
                console.log(res)
                setMessage(res.data.message);
                return getAllFileNames();
            })
            .then((files) => {
                // console.log(files)
                // setFileInfos(files.data);
                // console.log(fileInfos)
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
        <div>
            <div className="row">
                <div className="col-8">
                    <label className="btn btn-default p-0">
                        <input type="file" onChange={selectFile}/>
                    </label>
                </div>

                <div className="col-4">
                    <button
                        className="btn btn-success btn-sm"
                        disabled={!currentFile}
                        onClick={upload}
                    >
                        Upload
                    </button>
                </div>
            </div>

            {currentFile && (
                <div className="progress my-3">
                    <div
                        className="progress-bar progress-bar-info"
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
                <div className="alert alert-secondary mt-3" role="alert">
                    {message}
                </div>
            )}

            <div className="card mt-3">
                <div className="card-header">List of Files</div>
                <ul className="list-group list-group-flush">
                    {/*{fileInfos &&*/}
                    {/*    fileInfos.map((file, index) => (*/}
                    {/*        <li className="list-group-item" key={index}>*/}
                    {/*            <a href={''}>{file.file}</a>*/}
                    {/*        </li>*/}
                    {/*    ))}*/}
                </ul>
            </div>
        </div>
    );
};

export default FileUpload;