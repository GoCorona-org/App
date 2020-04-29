import React from 'react';
import { StyleSheet, View } from 'react-native';
import Modal from 'react-native-modal';

class Popup extends React.Component {
    render() {
        return (
            <Modal backdropColor={"white"} style={styles.modal} isVisible={this.props.isVisible}>
                <View style={styles.container}>
                    {this.props.children}
                </View>
            </Modal >
        )
    }
}
export default Popup;

const styles = StyleSheet.create({
    container: {
        width: 242,
        height: 242,
        padding: 20,
        alignItems: "center",
        borderWidth: 1,
        borderColor: "#E72E68",
        borderRadius: 20,
        backgroundColor: "white"
    },
    modal: {
        alignItems: "center"
    },
    question: {
        width: 180,
        fontSize: 15,
        letterSpacing: 0.6,
        textAlign: "center",
        // fontFamily: "Roboto",
        lineHeight: 24
    },
    button1: {
        width: 180,
        height: 50,
        margin: 8,
        backgroundColor: "#E72E68",
        borderRadius: 60
    },
    button2: {
        width: 180,
        height: 50,
        margin: 8,
        backgroundColor: "white",
        borderWidth: 1,
        borderColor: "#E72E68",
        borderRadius: 60
    },
    buttonlabel1: {
        color: "white"
    },
    buttonlabel2: {
        fontWeight: "bold"
    }
})

export const PopupStyles = styles;
