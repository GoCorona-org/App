import { Ionicons } from '@expo/vector-icons';
import React from 'react';
import { StyleSheet, Text, View, Image, ScrollView } from 'react-native';
import ProgressBarContainer from '../../../components/ProgressBar';
import CustomButton from '../../../components/button/index'
import { useNavigation } from '@react-navigation/core';


export default function CheckupWho() {
    const text = "Patient"
    const pageNo = 3;

    return (
        <ScrollView>
            <View style={styles.viewContainer}>
                <View style={styles.container}>
                    <View style={styles.sectionContainer}>
                        <Text style={styles.title}>Tell us who this checkup is for?</Text>
                        <View style={styles.helpLinks}>
                            <CustomButton label="Self"/>
                            <CustomButton label="Someone else and you're assisting them" />
                        </View>
                    </View>
          
                    <View style={styles.sectionContainer}>
                        <Text style={styles.title}>Or, if you dont have any symptoms</Text>
                        <View style={styles.helpLinks}>
                            <CustomButton label="Explore"
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
        alignSelf: 'flex-start',
        marginLeft: 35
    },
    helpLinks: {
        marginTop: 30,
        padding: 5,
        width: "90%"
    },

});
