import React, {useEffect, useState} from "react";
import FileService from "../../services/FileService";
import {useParams} from "react-router-dom";
import ModalWindow from "../ModalWindow/ModalWindow";
import FileUpload from "../Fileupload/FileUpload";
import delete_icon from "../../images/delete_icon.png";
import download_icon from "../../images/download_icon.png";
import SvgIcon from "../../images/svg.svg";
import PngIcon from "../../images/png.svg";
import CssIcon from "../../images/css.svg";
import GifIcon from "../../images/gif.svg";
import JpgIcon from "../../images/jpg.svg";
import JsIcon from "../../images/js.svg";
import Mp3Icon from "../../images/mp3.svg";
import PdfIcon from "../../images/pdf.svg";
import PhpIcon from "../../images/php.svg";
import SqlIcon from "../../images/sql.svg";
import TxtIcon from "../../images/txt.svg";
import XlsIcon from "../../images/xls.svg";
import XmlIcon from "../../images/xml.svg";
import ZipIcon from "../../images/zip.svg";
import DocxIcon from "../../images/docx.svg";
import "./FileList.css";

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

    const textTruncate = (str: string) => {
        const arrFileExt = str.split('.');
        const fileExt = arrFileExt[arrFileExt.length - 1];
        arrFileExt.pop();
        str = arrFileExt.join('.');
        return (str.length) > 12 ? str.substring(0, 12) + "..." + fileExt : str + '.' + fileExt;
    }

    const iconManager = (str: string) => {
        switch (str.split('.').pop()) {
            case 'svg':
                return SvgIcon;
            case  'png':
                return PngIcon;
            case 'css':
                return CssIcon;
            case 'gif':
                return GifIcon;
            case 'jpg':
                return JpgIcon;
            case 'js':
                return JsIcon;
            case 'mp3':
                return Mp3Icon;
            case 'pdf':
                return PdfIcon;
            case 'php':
                return PhpIcon;
            case 'sql':
                return SqlIcon;
            case 'txt':
                return TxtIcon;
            case 'xls':
                return XlsIcon;
            case 'xml':
                return XmlIcon;
            case 'zip':
                return ZipIcon;
            case 'docx':
                return DocxIcon;
        }
    }

    // const iconManager = (str: string) => {
    //     const tmp = str.split('.').pop() ?? '';
    //     console.log((tmp[0].toUpperCase()+tmp.substring(1)+'Icon'))
    //     return (tmp[0].toUpperCase()+tmp.substring(1)+'Icon')
    // }

    // const iconManager = (str: string) => {
    //     const tmp: string = str.split('.').pop();
    //     return {
    //         'svg':SvgIcon,
    //         'png':PngIcon
    //     }[tmp]
    // }

    return (
        <>
            <ModalWindow
                getModalContent={<FileUpload updateFileList={getAllFiles} setVisible={setUploadFileFormVisibility}/>}
                setVisible={setUploadFileFormVisibility} visible={uploadFileFormVisibility}/>
            <div className="d-flex flex-column">
                <button type="button" className="btn btn-primary fs-5 w-100 w-25" onClick={() => {
                    setUploadFileFormVisibility(true)
                }}>Add file
                </button>
                {errMsg ? errMsg :
                    <div className="container row justify-content-center justify-content-md-start mt-5">
                        {fileList && fileList.map((file, index) => {
                            return (
                                <div key={index}
                                     className="card border border-secondary m-1 h-100 d-flex flex-column justify-content-center align-items-center"
                                     style={{maxWidth: '10rem', minHeight: '12rem', maxHeight: '14rem'}}>
                                    <img src={iconManager(file)} style={{maxWidth: '5rem'}}/>
                                    <div className="w-100 d-block text-truncate text-center text-wrap">
                                        {textTruncate(file)}
                                    </div>
                                    <div>
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
                                </div>
                            )
                        })
                        }
                    </div>
                }
            </div>
        </>

    )
}

export default FileList;