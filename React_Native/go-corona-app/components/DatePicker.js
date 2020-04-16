import React, { Component } from "react";
import { Modal, StyleSheet, View, Text } from "react-native";
import { Calendar } from "react-native-calendars";
import { Ionicons } from "@expo/vector-icons";


/**
 * Was working on this to select a custom range with some ranges disabled 
 * but skipped this due to a lot of issues 
 */
export default function DatePicker({ sDate, eDate, visible, closeCallback }) {
  const [startDate, setStartDate] = React.useState(sDate || new Date());
  const show = () => {
    setVisible(true);
  };
  const hide = () => {
    setVisible(false);
  };
  let ed = new Date();
  ed.setDate(ed.getDate() + 3);
  const [endDate, setEndDate] = React.useState(eDate || ed);

  const getMarkedDateObject = (startDate, endDate) => {
    let deltaDays =
      (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
    let markedDates = {};
    let start = startDate;
    for (var i = 0; i < deltaDays; i++) {
      start.setDate(i == 0 ? startDate.getDate() : startDate.getDate() + 1);
      markedDates[formatDate(start)] = {
        startingDay: i == 0,
        endingDay: i == deltaDays - 1,
        selectedDay: true,
        color: "green",
      };
    }
    return markedDates;
  };

  const formatDate = (date, type) => {
    if (type == "IN")
      return (
        "" + date.getDate() + "/" + date.getMonth() + "/" + date.getFullYear()
      );
    else
      return date.getFullYear() + "/" + date.getMonth() + "/" + date.getDate();
  };

  return (
    <Modal visible={visible} transparent={true} onRequestClose={() => { }}>
      <View style={styles.modalContainerStyle}>
        <Text>Please select start and end date</Text>
        <Calendar
          markedDates={getMarkedDateObject(startDate, endDate)}
          markingType={"period"}
        />
        <Ionicons name={"md-close"} size={30} onPress={closeCallback} />
      </View>
    </Modal>
  );
}
const styles = StyleSheet.create({
  modalContainerStyle: {
    margin: "10%",
    backgroundColor: "white",
    borderRadius: 20,
    padding: 35,
    alignItems: "center",
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 5,
  },
});