import * as FileSystem from 'expo-file-system';
import JSZip from "jszip"
import JSZipUtils from "jszip-utils"
import JsonPath from "jsonpath"

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

const getBinEntry = (roundedDate) => {
    const day = `${roundedDate.getDate()}`.padStart(2, '0')
    const month = `${roundedDate.getMonth() + 1}`.padStart(2, '0')
    const year = `${roundedDate.getFullYear()}`
    const hours = `${roundedDate.getHours()}`.padStart(2, '0')
    const minutes = `${roundedDate.getMinutes()}`.padStart(2, '0')

    return `${day}:${month}:${year}:${hours}:${minutes}:00`
}

// TODO: need to be generated using uuid; and stored securely on the device
const UserLocationId = 'aanc-ijsu-9198-aisd'

const formatData = data => {
    return {
        UserLocationId,
        Location: {
            Latitude: data.latitudeE7,
            Longitude: data.longitudeE7,
            ts: data.timestampMs
        }
    }
}

export async function binHistoryData(data) {
    const binnedData = data.reduce((acc, d) => {
        const binEntry = getBinEntry(roundToNearest10Min(d.timestampMs))
        return {
            ...acc,
            [binEntry]: acc[binEntry] ? acc[binEntry].concat(formatData(d)) : [formatData(d)]
        }
    }, {})

    return binnedData
}

export async function unzipArchive(fileUri) {
    const fileInBinary = await getFileInBinary(fileUri)

    console.log('unzipping: ', fileUri)

    const zip = await JSZip.loadAsync(fileInBinary)

    console.log('done unzipping')

    return zip
}