
import React, { Component, useState, useEffect, useRef } from 'react';
import { ActivityIndicator, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { MaterialCommunityIcons } from '@expo/vector-icons';

import { amIExposedApi } from '../../constants/AppSettings';
import Http from '../../services/Http';
import { getUUIDs } from '../../utils/helpers';

const ExposureBtn = () => {
    const [exposure, setExposure] = useState(null);

    useEffect(() => {
        fetchCurrentExposure();
    }, [])

    const fetchCurrentExposure = async () => {
        const uids = await getUUIDs()
        try {
            const response = await Http.get(amIExposedApi + '/' + uids.medicalUUID);
            const data = response.data;
            console.log('exposure', data)
            const { Exposure } = data;
            setExposure(Exposure)
        } catch (error) {
            console.log('error in calling am I exposed', error)
            setExposure('unknown')
        }
    }

    const getColor = (exposure) => {
        switch (exposure) {
            case 'unknown':
                return 'grey';
            case 'exposed':
                return 'yellow';
            case 'positive':
                return 'red';
            default:
                return 'grey';
        }
    }

    return (
        exposure ?
            <TouchableOpacity
                style={[styles.exposureBtn, { backgroundColor: getColor() }]}
                onPress={() => {
                    fetchCurrentExposure()
                }}
            >
                <MaterialCommunityIcons color={'white'} name='refresh' size={24} />
            </TouchableOpacity> : <ActivityIndicator></ActivityIndicator>
    )
}

const styles = StyleSheet.create({
    exposureBtn: {
        backgroundColor: '#fff',
        position: 'absolute',
        top: 20,
        right: 10,
        padding: 15,
        elevation: 3,
        alignItems: 'center',
        alignSelf: 'flex-end',
        justifyContent: 'center',
        borderRadius: 50
    },
})

export default ExposureBtn;