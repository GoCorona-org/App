import { useNavigation } from '@react-navigation/native';
import React from 'react';
import { KeyboardAvoidingView, StyleSheet, Text, TextInput, View } from 'react-native';
import { CheckBox } from 'react-native-elements';
import Logo from '../../assets/images/logo.svg';
import Button from '../../components/button/Button';
import { signInWithGoogleAsync } from '../../utils/Login';
import { setItem } from '../../utils/Storage';

export default class LoginScreen extends React.Component {
    state = {
        phone: "",
        password: "",
        loginProgress: false,
        loggedInUser: null,
        checked: false
    }
    setLoginProgress(loginProgress) {
        this.setState({ loginProgress })
    }
    setloggedInUser(loggedInUser) {
        this.setState({ loggedInUser })
    }

    onLoginPress = async () => {
        this.setLoginProgress(true)
        const result = await signInWithGoogleAsync();
        if (!result.cancelled && !result.error) {
            await setItem('login', result);
            console.log('user logged-in successfully! - ', result.accessToken)
            this.setLoginProgress(false)
            this.setloggedInUser(result)
        } else {
            console.log('failed to login')
            this.setLoginProgress(false)
        }
    };

    toggleCheckbox = () => {
        const { checked } = this.state
        this.setState({ checked: !checked })
    }

    render() {
        return (
            <KeyboardAvoidingView style={{ flex: 1, backgroundColor: "#FFFFFF" }}>
                <View style={styles.container}>
                    <View style={styles.logo}><Logo width="150" height="150" /></View>
                    <View style={styles.loginContainer}>
                        <TextInput placeholder={"Phone number"} placeholderTextColor={"#989898"} style={styles.phoneno} keyboardType={"number-pad"}
                            value={this.state.phone} onChangeText={(val) => this.setState({ phone: val })} />
                        <TextInput secureTextEntry={true} placeholder={"Password"} placeholderTextColor={"#989898"} style={styles.password}
                            value={this.state.password} onChangeText={(val) => this.setState({ password: val })} />
                        <View style={styles.options}>
                            <View style={{ flexDirection: "row", alignItems: "center" }}>
                                <CheckBox checked={this.state.checked} onIconPress={this.toggleCheckbox} size={20} containerStyle={styles.checkbox}></CheckBox>
                                <Text style={styles.rememberMe}>Remember me</Text></View>
                            <Text style={styles.forgotPassword}>Forgot Password</Text>
                        </View>
                        <Button labelStyle={styles.loginButtonLabel} style={styles.loginButton} label={"Login"} />
                        <View style={styles.loginOptions}><Text style={styles.loginOptionsText}>Or login using </Text><Text onPress={this.onLoginPress} style={styles.googleText}>Google</Text></View>
                        <View style={styles.signup}><Text style={styles.accountText}>Don't have an account yet? </Text><Text onPress={this.props.goToSignUpScreen && this.props.goToSignUpScreen()} style={styles.signupText}>Signup</Text></View>
                    </View>
                </View>
            </KeyboardAvoidingView>
        )
    }
}

export function Loginscreen() {
    const navigation = useNavigation()
    function goToSignup() { navigation.navigate('SignUp'); }
    return <LoginScreen goToSignUpScreen={() => goToSignup}></LoginScreen>
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: "center",
        justifyContent: "center"
    },
    logo: {
        padding: 20,
        paddingTop: 30
    },
    loginContainer: {
        width: "100%",
        alignItems: "center",
        padding: 10
    },
    phoneno: {
        height: 45,
        width: "80%",
        borderRadius: 8,
        borderColor: "black",
        borderWidth: 1,
        backgroundColor: "#EEEEEE",
        padding: 10,
        margin: 10
    },
    password: {
        height: 45,
        width: "80%",
        borderRadius: 8,
        borderColor: "black",
        borderWidth: 1,
        backgroundColor: "#EEEEEE",
        padding: 10,
        margin: 10
    },
    options: {
        flexDirection: "row",
        width: "80%",
        justifyContent: "space-between",
        alignItems: "center"
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
        color: "#989898"
    },
    forgotPassword: {
        fontSize: 11,
        fontWeight: "bold",
        color: "#989898"
    },
    loginButton: {
        width: 125,
        margin: 20,
        borderRadius: 8,
        height: 45,
        backgroundColor: "#E03D51"
    },
    loginButtonLabel: {
        fontSize: 18,
        letterSpacing: 0.7,
        color: "#F8F8F8"
    },
    loginOptions: {
        flexDirection: "row",
        justifyContent: "center",
        margin: 10
    },
    loginOptionsText: {
        fontSize: 11,
        textAlign: "center",
        fontWeight: "bold",
        color: "#989898"
    },
    googleText: {
        fontSize: 11,
        textAlign: "center",
        fontWeight: "bold",
        color: "#E03D51"
    },
    signup: {
        flexDirection: "row",
        justifyContent: "center",
        margin: 10
    },
    accountText: {
        fontSize: 11,
        textAlign: "center",
        fontWeight: "bold",
        color: "#989898"
    },
    signupText: {
        fontSize: 11,
        textAlign: "center",
        fontWeight: "bold",
        color: "#E03D51"
    }
});

export const LoginStyles = styles;