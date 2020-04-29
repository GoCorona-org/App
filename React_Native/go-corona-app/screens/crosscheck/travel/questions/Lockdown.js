import React, { Component, useState, useEffect } from "react";
import { StyleSheet, View, Text, Image } from "react-native";
import { ScrollView, RectButton } from "react-native-gesture-handler";
import LockdownImage from "../../../../assets/images/Lockdown.svg";
import { RadioButton, Divider } from "react-native-material-ui";
import { Input, CheckBox } from "react-native-elements";
import Autocomplete from "react-native-autocomplete-input";
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scrollview';

export default function Lockdown({ questions, setValues }) {
  const [hometown, setHometown] = useState('');
  const [currentLocation, setCurrentLocation] = useState('');
  const [flight, setLocalFlight] = useState(false);
  const [train, setTrain] = useState(false);
  const [auto, setAuto] = useState(false);
  const [cab, setCab] = useState(false);

  useEffect(() => {
    const name = questions[0].name
    setValues([{ name, value: hometown}])
  }, [hometown])

  useEffect(() => {
    const name = questions[1].name
    setValues([{ name, value: currentLocation}])
  }, [currentLocation])

  useEffect(() => {
    const name = questions[2].name
    setValues([{ name, value: flight}])
  }, [flight])

  useEffect(() => {
    const name = questions[3].name
    setValues([{ name, value: train}])
  }, [train])

  useEffect(() => {
    const name = questions[4].name
    setValues([{ name, value: auto}])
  }, [auto])

  useEffect(() => {
    const name = questions[5].name
    setValues([{ name, value: cab}])
  }, [cab])

  return (
    <KeyboardAwareScrollView style={styles.containerStyle}>
      <View style={{ flexDirection: "column" }}>
        <Text style={styles.headerQuestTextStyle}>
          Stranded due to the lockdown?
        </Text>
        <LockdownImage
          width="340"
          height="100"
          style={{ alignSelf: "center", marginTop: 10 }}
        />
        <Text style={styles.subQuestionStyle}>
          Which city are you from and where are you staying now?
        </Text>

        <View>
          <Input placeholder="Hometown" onChangeText={setHometown} />
        </View>

        <Divider />
        <View style={{ paddingTop: 10 }}>
          <Input placeholder="Current location" onChangeText={setCurrentLocation} />
        </View>
        <Divider />

        <Text style={styles.subQuestionStyle}>
          What modes of transport have you used for commute in the last 30 days?
        </Text>
        <Divider />
        <CheckBox
          title="Flight"
          containerStyle={styles.noBorder}
          onPress={() => { setLocalFlight(!flight) }}
          checked={flight}
          checkedColor="#E03D51"
          uncheckedColor="#D2D2D2"
        />
        <CheckBox
          title="Train"
          containerStyle={styles.noBorder}
          onPress={() => { setTrain(!train) }}
          checked={train}
          checkedColor="#E03D51"
          uncheckedColor="#D2D2D2"
        />
        <Divider />
        <CheckBox
          title="Auto rickshaw"
          containerStyle={styles.noBorder}
          checkedColor="#E03D51"
          uncheckedColor="#D2D2D2"
          onPress={() => { setAuto(!auto) }}
          checked={auto}
        />
        <Divider />
        <CheckBox
          title="Cab"
          containerStyle={styles.noBorder}
          checkedColor="#E03D51"
          uncheckedColor="#D2D2D2"
          onPress={() => { setCab(!cab) }}
          checked={cab}
        />
        <Divider />
      </View>
    </KeyboardAwareScrollView>
  );
}

const styles = StyleSheet.create({
  containerStyle: {
    paddingLeft: "5%",
    paddingRight: "5%"
  },

  headerQuestTextStyle: {
    fontWeight: "bold",
    padding: "5%",
  },

  subQuestionStyle: {
    fontSize: 14,
    margin: 20,
    textAlign: "center"
  },

  noBorder: {
    borderWidth: 0

  },

});
