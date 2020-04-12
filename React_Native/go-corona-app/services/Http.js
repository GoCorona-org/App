import axios from 'axios';
import { baseUrl } from '../constants/AppSettings';
// response schema
// {
//     // `data` is the response that was provided by the server
//     data: { },

//     // `status` is the HTTP status code from the server response
//     status: 200,

//         // `statusText` is the HTTP status message from the server response
//         statusText: 'OK',

//             // `headers` the HTTP headers that the server responded with
//             // All header names are lower cased and can be accessed using the bracket notation.
//             // Example: `response.headers['content-type']`
//             headers: { },

//     // `config` is the config that was provided to `axios` for the request
//     config: { },

//     // `request` is the request that generated this response
//     // It is the last ClientRequest instance in node.js (in redirects)
//     // and an XMLHttpRequest instance in the browser
//     request: { }
// }
export default Http = {
    get: (url, params) => {
        return axios.get(url, params);
    },
    post: (url, body, options) => {
        return axios.post(url, body, options);
    },
    put: (url, body, options = null) => {
        return axios.put(url, body, options);
    },
    delete: (url, params = null) => {
        return axios.delete(url, params);
    },
    request: (config) => {
        return axios(config);
    }
}
Object.freeze(Http);


axios.interceptors.request.use(async function (config) {
    addBaseUrl(config);
    // TODO: get token from storage
    addToken(config, '')
    return config;
}, function (error) {
    return Promise.reject(error);
});


function addBaseUrl(config) {
    if (!config.url.trim().startsWith('http')) {
        config.url = baseUrl + config.url;
    }
}

function addToken(config, token) {
    config.headers['Authorization'] = 'Bearer ' + token;
}
