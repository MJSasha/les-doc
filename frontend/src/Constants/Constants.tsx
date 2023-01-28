const api_url = function () {
    let api_url = '';
    if (process.env.NODE_ENV == 'local') {
        api_url = 'http://localhost:8080/api/';
    } else if (process.env.NODE_ENV == 'development') {
        api_url = 'http://localhost:8080/api/';
    } else if (process.env.NODE_ENV == 'production') {
        api_url = 'http://localhost:8080/api/';
    }
    return api_url;
}

export const url = api_url();