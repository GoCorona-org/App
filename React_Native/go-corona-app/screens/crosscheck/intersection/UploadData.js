import { Ionicons } from '@expo/vector-icons';
import React, { useState } from 'react';
import { StyleSheet, Text, View, Image, ScrollView } from 'react-native';
import * as DocumentPicker from 'expo-document-picker';
import * as WebBrowser from 'expo-web-browser';

import ProgressBarContainer from '../../../components/ProgressBar';
import CustomButton from '../../../components/button/index'
import QuestionIcon from '../../../assets/images/Question.svg'
import LinkIcon from '../../../assets/images/Link.svg'
import UploadIcon from '../../../assets/images/Upload.svg'
import TickIcon from '../../../assets/images/Tick.svg'
import Separator from '../../../components/Separator';

import { unzipArchive, getLocationHistoryFile, filterJsonData, binHistoryData } from '../../../utils/ProcessData'

export default function UploadDataScreen() {
    const [uploadedFileMeta, setUploadedFileMeta] = useState(null)
    const [uploadInProgress, setUploadInProgress] = useState(false)

    const handleUploadFilePress = async () => {
        setUploadInProgress(true)

        const result = await DocumentPicker.getDocumentAsync({ type: 'application/zip' })
        if (result.type === "success") {
            setUploadedFileMeta(result)

            const unzippedFiles = await unzipArchive(result.uri)
            const json_file = await getLocationHistoryFile(unzippedFiles)

            if (json_file) {
                const filteredTimestamps = await filterJsonData(json_file)
                const binnedData = await binHistoryData(filteredTimestamps)
                console.log(binnedData)
                //TODO: API Integration

            } else {
                console.log('Could not find Location History file')
            }

        }
        console.log(result)
        setUploadInProgress(false)
    }

    const text = "Patient"
    const pageNo = 3;

    return (
        <ScrollView>
            <View style={styles.viewContainer}>
                <ProgressBarContainer textOnTop={text} currPage={pageNo} totalPages={3} />
                <View style={styles.container}>
                    <View style={styles.sectionContainer}>
                        <Text style={styles.title}>Run the tutorial and download data</Text>
                        <View style={styles.helpLinks}>
                            <CustomButton
                                Icon={QuestionIcon}
                                label="Take me through the tutorial"
                                onPress={() => WebBrowser.openBrowserAsync('https://support.google.com/accounts/answer/3024190?hl=en')}
                            />
                            <CustomButton
                                Icon={LinkIcon}
                                label="I am all set, take me to Google for downloads"
                                onPress={() => WebBrowser.openBrowserAsync('https://takeout.google.com/')}
                            />
                        </View>
                    </View>
                    <Separator />
                    <View style={styles.sectionContainer}>
                        <Text style={styles.title}>Upload the Google Takeout Zip file</Text>
                        <View style={styles.helpLinks}>
                            <CustomButton
                                Icon={uploadedFileMeta ? TickIcon : UploadIcon}
                                label={uploadedFileMeta ? "Done" : "Upload here"}
                                loading={uploadInProgress}
                                disabled={uploadedFileMeta ? true : false}
                                onPress={handleUploadFilePress}
                            />
                        </View>
                    </View>
                </View>
            </View>
        </ScrollView>
    );
}

const styles = StyleSheet.create({
    viewContainer: {
        flex: 1,
        backgroundColor: '#fafafa',
        justifyContent: 'flex-start'
    },
    container: {
        justifyContent: 'center'
    },
    sectionContainer: {
        marginTop: 40,
        marginBottom: 40,
        alignItems: 'center',
    },
    title: {
        fontSize: 18,
        fontWeight: "bold",
    },
    helpLinks: {
        marginTop: 30,
        padding: 5,
        width: "90%"
    },
    uploadedFileMessage: {
        flexWrap: "wrap",
        textAlign: "center"
    }
});
