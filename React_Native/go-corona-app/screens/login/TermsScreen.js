import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import Button from '../../components/button/Button';

export default class TermsScreen extends React.Component {
    render() {
        const terms = "Your personal information including your location and medical history collected will remain confidential and will not be shared with any third party providers, advertisers or other Spot-Corona users. The information collected is strictly used to help track the spread of COVID-19 in your area and to help serve the general public. You will be notified of any changes to this policy and your information will not be used for any other purposes without prior notice and approval from your end."
        return (
            <View style={styles.container}>
                <Text style={styles.header}>Terms and Conditions</Text>
                <Text style={styles.terms}>{terms}</Text>
                <Button onPress={this.props.onBackPress && this.props.onBackPress()} labelStyle={styles.backButtonLabel} style={styles.backButton} label={"Back"}></Button>
            </View>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#FFFFFF",
        padding: 50
    },
    header: {
        marginBottom: 50,
        fontSize: 13,
        fontWeight: "bold",
        color: "#3B24B1",
        letterSpacing: 0.7
    },
    terms: {
        color: "#383838",
        lineHeight: 24
    },
    backButton: {
        backgroundColor: "#FFFFFF",
        margin: 30
    },
    backButtonLabel: {
        color: "#3B24B1",
        letterSpacing: 0.7,
        fontSize: 15
    }
})