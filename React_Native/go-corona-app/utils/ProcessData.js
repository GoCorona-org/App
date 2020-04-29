import * as FileSystem from 'expo-file-system';
import JSZip from "jszip"
import JSZipUtils from "jszip-utils"
import JsonPath from "jsonpath"
import { getUUIDs } from './helpers';

// Unzip - https://github.com/smoll/crna-zipfile/blob/master/App.js

async function getFileInBinary(fileUri) {
    const data = await new JSZip.external.Promise((resolve, reject) => {
        JSZipUtils.getBinaryContent(fileUri, (err, data) => {
            if (err) {
                reject(err)
            } else {
                resolve(data)
            }
        })
    })
    return data
}

export async function getLocationHistoryFile(zip) {
    console.log('find Location History.json')

    const filteredResults = zip.filter((relativePath, file) => relativePath.endsWith('History.json'))

    if (filteredResults.length > 0) {
        console.log('found Location History.json file')

        const locationFile = filteredResults[0]
        const fileContent = await locationFile.async('binarystring')
        return JSON.parse(fileContent)
    }

    return null
}

export async function filterJsonData(data) {
    // all data after '31st January 2020'
    const timeStampForJan312020 = "1580428800000"
    const timestamps = JsonPath.query(data, `$.locations[?(@.timestampMs>=${timeStampForJan312020})]`)

    return timestamps
}

const roundToNearest10Min = (tsMs) => {
    const roundedTsMs = Math.round(tsMs / 1000 / 60 / 10) * 10 * 60 * 1000;
    const roundedDate = new Date(roundedTsMs);

    return roundedDate
}

// {  
//    "id" : "p1",
//    "location_history" : [ 
//        { "timeslot": "00.00.01.15.2020", "lat" : "12.3456","long" : "13.456", "status" : "unknown"},
//        { "timeslot": "00.10.01.15.2020", "lat" : "13.3456","long" : "14.456", "status" : "unknown"}
//    ]
// }

const getBinEntry = (roundedDate) => {
    const day = `${roundedDate.getDate()}`.padStart(2, '0')
    const month = `${roundedDate.getMonth() + 1}`.padStart(2, '0')
    const year = `${roundedDate.getFullYear()}`
    const hours = `${roundedDate.getHours()}`.padStart(2, '0')
    const minutes = `${roundedDate.getMinutes()}`.padStart(2, '0')

    return `${hours}.${minutes}.${month}.${day}.${year}`
}

// TODO: need to be generated using uuid; and stored securely on the device
const formatData = data => {
    return {
        timeslot: getBinEntry(roundToNearest10Min(data.timestampMs)),
        lat: data.latitudeE7,
        long: data.longitudeE7,
        status: "unknown"
    }
}

export async function binHistoryData(data) {
    // const binnedData = data.reduce((acc, d) => {
    //     const binEntry = getBinEntry(roundToNearest10Min(d.timestampMs))
    //     return {
    //         ...acc,
    //         [binEntry]: acc[binEntry] ? acc[binEntry].concat(formatData(d)) : [formatData(d)]
    //     }
    // }, {})
    const formattedData = data.map(formatData)
    const UUIDs = await getUUIDs()

    return { id: UUIDs.locationUUID, location_history: formattedData }
}

// NOTE: old way of binning which is not followed in the current version of the API; might need it in future

// const formatData = data => {
//     return {
//         UserLocationId,
//         Location: {
//             Latitude: data.latitudeE7,
//             Longitude: data.longitudeE7,
//             ts: data.timestampMs
//         }
//     }
// }

// export async function binHistoryData(data) {
//     const binnedData = data.reduce((acc, d) => {
//         const binEntry = getBinEntry(roundToNearest10Min(d.timestampMs))
//         return {
//             ...acc,
//             [binEntry]: acc[binEntry] ? acc[binEntry].concat(formatData(d)) : [formatData(d)]
//         }
//     }, {})

//     return binnedData
// }

export async function unzipArchive(fileUri) {
    const fileInBinary = await getFileInBinary(fileUri)

    console.log('unzipping: ', fileUri)

    const zip = await JSZip.loadAsync(fileInBinary)

    console.log('done unzipping')

    return zip
}