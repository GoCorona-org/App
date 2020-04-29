import { useNavigation } from '@react-navigation/native';
import React from 'react';
import { KeyboardAvoidingView, Modal, StyleSheet, Text, TextInput, View } from 'react-native';
import { CheckBox } from 'react-native-elements';
import Button from '../../components/button/Button';
import TermsScreen from '../login/TermsScreen';

export default class SignupScreen extends React.Component {
    state = {
        name: "",
        age: "",
        gender: "",
        email: "",
        phone: "",
        password: "",
        checked: false,
        visible: false
    }

    userDetails = {

    }

    toggleCheckbox = () => {
        const { checked } = this.state
        this.setState({ checked: !checked })
    }

    onBackPress = () => {
        this.setState({ visible: false })
    }

    render() {
        return (
            <KeyboardAvoidingView style={{ flex: 1, backgroundColor: "#FFFFFF" }} >
                <View style={{ flex: 1, alignItems: "center", justifyContent: "center", padding: 10, backgroundColor: "#FFFFFF" }}>
                    <TextInput placeholder={"Name"} placeholderTextColor={"#989898"} style={styles.input} keyboardType={""}
                        value={this.state.name} onChangeText={(val) => this.setState({ name: val })} />
                    <TextInput placeholder={"Age"} placeholderTextColor={"#989898"} style={styles.input} keyboardType={"number-pad"}
                        value={this.state.age} onChangeText={(val) => this.setState({ age: val })} />
                    <TextInput placeholder={"Gender"} placeholderTextColor={"#989898"} style={styles.input}
                        value={this.state.gender} onChangeText={(val) => this.setState({ gender: val })} />
                    <TextInput on placeholder={"E-mail"} placeholderTextColor={"#989898"} style={styles.input} keyboardType={"email-address"}
                        value={this.state.email} onChangeText={(val) => this.setState({ email: val })} />
                    <TextInput placeholder={"Phone number"} placeholderTextColor={"#989898"} style={styles.input} keyboardType={"number-pad"}
                        value={this.state.phone} onChangeText={(val) => this.setState({ phone: val })} />
                    <TextInput secureTextEntry={true} placeholder={"Password"} placeholderTextColor={"#989898"} style={styles.input}
                        value={this.state.password} onChangeText={(val) => this.setState({ password: val })} />
                    <View style={{ flexDirection: "row", padding: 5, alignItems: "center", justifyContent: "flex-start" }}>
                        <CheckBox checked={this.state.checked} onIconPress={this.toggleCheckbox} size={20} containerStyle={styles.checkbox}></CheckBox>
                        <Text style={styles.rememberMe}>I read and agreed to </Text><Text onPress={() => this.setState({ visible: true })} style={styles.terms}>Terms and Conditions</Text></View>
                    <Button onPress={this.props.onCreateAccountPress && this.props.onCreateAccountPress()} labelStyle={styles.loginButtonLabel} style={styles.loginButton} label={"CREATE ACCOUNT"} />
                    <View style={styles.signup}><Text style={styles.accountText}>Already have an account? </Text>
                        <Text onPress={this.props.onSignInPress && this.props.onSignInPress()} style={styles.signupText}>Signin</Text></View>
                    <Modal visible={this.state.visible} presentationStyle={"fullScreen"}>
                        <TermsScreen onBackPress={() => this.onBackPress}></TermsScreen>
                    </Modal>
                </View>
            </KeyboardAvoidingView>
        )
    }
}

export function Signupscreen() {
    const navigation = useNavigation()
    function goToLogin() {
        navigation.navigate('Login');
    }
    function goToOtp() {
        navigation.navigate('Otp');
    }
    return <SignupScreen onCreateAccountPress={() => goToOtp} onSignInPress={() => goToLogin}></SignupScreen>
}


const styles = StyleSheet.create({
    input: {
        height: 40,
        width: "80%",
        borderRadius: 8,
        borderColor: "black",
        borderWidth: 1,
        backgroundColor: "#EEEEEE",
        padding: 10,
        margin: 12
    },
    checkbox: {
        margin: 0,
        marginLeft: 0,
        marginRight: 2,
        padding: 0,
        backgroundColor: "white",
        borderWidth: 0
    },
    rememberMe: {
        fontSize: 11,
        fontWeight: "bold",
        color: "#989898",
        letterSpacing: 0.7
    },
    terms: {
        fontSize: 11,
        fontWeight: "bold",
        color: "#3B24B1",
        letterSpacing: 0.7
    },
    loginButton: {
        margin: 20,
        borderRadius: 30,
        height: 45,
        backgroundColor: "#E03D51"
    },
    loginButtonLabel: {
        fontSize: 18,
        letterSpacing: 0.8,
        color: "#F8F8F8",
        letterSpacing: 0.7
    },
    signup: {
        flexDirection: "row",
        justifyContent: "center",
        marginTop: 20
    },
    accountText: {
        fontSize: 11,
        textAlign: "center",
        color: "#989898",
        letterSpacing: 0.7
    },
    signupText: {
        fontSize: 11,
        textAlign: "center",
        fontWeight: "bold",
        color: "#3B24B1",
        letterSpacing: 0.7
    }
})

export const SignupStyles = styles;