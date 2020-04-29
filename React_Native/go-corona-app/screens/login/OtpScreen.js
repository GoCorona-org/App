import { useNavigation } from '@react-navigation/native';
import React from 'react';
import { KeyboardAvoidingView, StyleSheet, Text, TextInput, View } from 'react-native';
import Logo from '../../assets/images/logo.svg';
import Button from '../../components/button/Button';
import { SignupStyles } from '../login/SignupScreen';
import { LoginStyles } from '../LoginScreen';

export default class OtpScreen extends React.Component {
    constructor(props) {
        super(props);
        this.focusNextField = this.focusNextField.bind(this);
        this.inputs = {};
        this.state = {
            otp: [],
            onBackspace: false,
            timer: 35
        }
    }
    componentDidMount() {
        this.otpTimer = setInterval(() => {
            this.otpAutoSend();
        }, 1000);
    }

    componentWillUnmount() {
        clearInterval(this.otpTimer);
    }

    otpAutoSend = () => {
        if (this.state.timer === 0) {
            this.props.autoSendOtp && this.props.autoSendOtp();
        }
        else {
            this.setState((prevstate) => ({ timer: prevstate.timer - 1 }));
        }
    };

    inputOTP = [];

    focusNextField(id) {
        this.inputs[id].focus();
    }

    toggleInput(num, id) {
        if (num.split("").length === 1) {
            this.inputs[id].blur()
            this.focusNextField(id)
            this.state.otp.push(num);
        }
    }

    render() {
        return (
            <KeyboardAvoidingView style={styles.container}>
                <View style={LoginStyles.container}>
                    <View style={LoginStyles.logo}><Logo width="150" height="150" /></View>
                    <View style={LoginStyles.loginContainer}>
                        <View style={styles.inputs}>
                            <TextInput ref={input => {
                                this.inputs['one'] = input;
                            }} onChangeText={(num) => { this.toggleInput(num, 'two') }} keyboardType={"number-pad"} maxLength={1} style={styles.input}>
                            </TextInput>
                            <TextInput ref={input => {
                                this.inputs['two'] = input;
                            }} onKeyPress={({ nativeEvent }) => {
                                if (nativeEvent.key === 'Backspace') {
                                    this.setState({ onBackspace: true })
                                    this.toggleInput("0", 'one')
                                }
                            }} onChangeText={(num) => { this.toggleInput(num, 'three') }} keyboardType={"number-pad"} maxLength={1} style={styles.input}>
                            </TextInput>
                            <TextInput ref={input => {
                                this.inputs['three'] = input;
                            }} onKeyPress={({ nativeEvent }) => {
                                if (nativeEvent.key === 'Backspace') {
                                    this.setState({ onBackspace: true })
                                    this.toggleInput("0", 'two')
                                }
                            }} onChangeText={(num) => { this.toggleInput(num, 'four') }} keyboardType={"number-pad"} maxLength={1} style={styles.input}>
                            </TextInput>
                            <TextInput ref={input => {
                                this.inputs['four'] = input;
                            }} onKeyPress={({ nativeEvent }) => {
                                if (nativeEvent.key === 'Backspace') {
                                    this.setState({ onBackspace: true })
                                    this.toggleInput("0", 'three')
                                }
                            }} onChangeText={(num) => { this.toggleInput(num, 'five') }} keyboardType={"number-pad"} maxLength={1} style={styles.input}>
                            </TextInput>
                            <TextInput ref={input => {
                                this.inputs['five'] = input;
                            }} onKeyPress={({ nativeEvent }) => {
                                if (nativeEvent.key === 'Backspace') {
                                    this.setState({ onBackspace: true })
                                    this.toggleInput("0", 'four')
                                }
                            }} onChangeText={(num) => { this.toggleInput(num, 'five') }} keyboardType={"number-pad"} maxLength={1} style={styles.input}>
                            </TextInput>
                        </View>
                        <View>
                            <Text style={styles.messageRequest}>Please check your text messages for the code</Text>
                        </View>
                        <View style={styles.autoSendLabelContainer}>
                            <Text style={[styles.messageRequest]}>OTP auto resend in </Text><Text style={LoginStyles.signupText}>{this.state.timer} seconds</Text>
                        </View>
                        <Button labelStyle={SignupStyles.loginButtonLabel} style={[SignupStyles.loginButton]} label={"Verify OTP"} />
                        <View style={[LoginStyles.loginOptions, { margin: 20 }]}><Text onPress={this.onLoginPress} style={LoginStyles.googleText}>Resend OTP</Text></View>
                    </View>
                </View >
            </KeyboardAvoidingView>
        )
    }
}


export function Otpscreen() {
    const navigation = useNavigation()
    function goToHome() {
        navigation.navigate('Login');
    }
    return <OtpScreen onSignInPress={() => goToHome}></OtpScreen>
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#FFFFFF"
    },
    inputs: {
        flexDirection: "row"
    },
    input: {
        height: 45,
        width: 45,
        borderRadius: 8,
        borderColor: "black",
        borderWidth: 1,
        backgroundColor: "#EEEEEE",
        padding: 15,
        paddingBottom: 10,
        paddingRight: 8,
        margin: 3,
        fontSize: 18
    },
    messageRequest: {
        fontSize: 11,
        letterSpacing: 0.8,
        color: "#989898",
        letterSpacing: 0.5,
        margin: 5,
        marginRight: 0
    },
    autoSendLabelContainer: {
        margin: 15,
        flexDirection: "row",
        alignItems: "center"
    }
})