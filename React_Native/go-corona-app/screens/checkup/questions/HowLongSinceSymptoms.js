import React, { Component } from "react";
import { StyleSheet, View, Text, Image } from "react-native";
import {
  ScrollView,
  TouchableOpacity,
} from "react-native-gesture-handler";
import { Divider } from "react-native-material-ui";
import HowLongSinceSymptomsFeature from "../../../assets/images/HowLongSinceSymptomsFeature";
import { AntDesign } from "@expo/vector-icons";

export default function HowLongSinceSymptoms(props) {
  const [selectedFever, setSelectedFever] = React.useState(null);
  const [selectedCough, setSelectedCough] = React.useState(null);
  const [selectedHeadache, setSelectedHeadache] = React.useState(null);
  const [selectedProgressValue, setSelectedProgressValue] = React.useState(
    null
  );
  return (
    <ScrollView style={styles.containerStyle}>
      <View style={{ flexDirection: "column" }}>
        <Text style={styles.headerQuestTextStyle}>
          How long have you had these symptoms for?
        </Text>
        <HowLongSinceSymptomsFeature
          width="200"
          height="120"
          style={{ alignSelf: "center", marginTop: 10 }}
        />
        <Text style={styles.subQuestionStyle}>
          Select the numbers of days for each
        </Text>
        <Selector
          title={"Fever"}
          onItemSelected={(value) => {
            setSelectedFever(value);
          }}
          selectedItem={selectedFever}
        />
        <Selector
          title={"Cough"}
          onItemSelected={(value) => {
            setSelectedCough(value);
          }}
          selectedItem={selectedCough}
          marginTop={10}
        />
        <Selector
          title={"Headache"}
          onItemSelected={(value) => {
            setSelectedHeadache(value);
          }}
          selectedItem={selectedHeadache}
          marginTop={10}
        />

        <View style={{ marginTop: 20, justifyContent: "center" }}>
          <Text style={{ marginBottom: 10, textAlign: "center" }}>
            How have the symptoms progressed over the last 48 hours?
          </Text>
          <View>
            <Divider />
          </View>
          <View style={{ marginTop: 32 }}>
            <Divider />
          </View>
          <ProgressSelector
            onItemSelected={(value) => {
              setSelectedProgressValue(value);
            }}
            selectedItem={selectedProgressValue}
          />
        </View>
      </View>
    </ScrollView>
  );
}

function ProgressSelector({ onItemSelected, selectedItem, marginTop }) {
  return (
    <View style={{ marginTop: marginTop }}>
      <View
        style={{
          flexDirection: "row",
          alignItems: "stretch",
          paddingLeft: "5%",
          paddingRight: "5%",
          marginTop: "2%",
        }}
      >
        {["Improved", "No Change", "Worsened", "Worsened mildly", "Worsened considerably"].map((d, i, vals) => {
          return (
            <View key={`howLong-${i}`} style={{ flex: 1 }}>
              <TouchableOpacity
                onPress={() => {
                  onItemSelected(d);
                }}
              >
                <RectangularRadioSelector
                  selected={d === selectedItem}
                  text={d}
                  curveTopLeft={i == 0}
                  curveTopRight={i == vals.length - 1}
                />
              </TouchableOpacity>
            </View>
          );
        })}
      </View>
    </View>
  );
}

function RectangularRadioSelector({
  text,
  selected,
  curveTopLeft,
  curveTopRight,
}) {
  return (
    <View style={{ flexDirection: "column",justifyContent:"center" }}>
      <View
        style={{
          height: 42,
          borderTopLeftRadius: curveTopLeft ? 8 : 0,
          borderTopRightRadius: curveTopRight ? 8 : 0,
          borderColor: "white",
          borderWidth: 1,
          backgroundColor: selected ? "#A9E7CB" : "#E9E9E9",
          justifyContent: "center",
        }}
      >
      </View>
    <View style={ { marginLeft:30,opacity:selected ? 1 : 0 }}>
      <AntDesign
        name="caretup" size={10}/>
      </View>
        
      <Text
          style={{
            textAlign: "center",
            color: "#909090",
            fontSize:8,
            fontWeight:"bold"
          }}
        >
          {text}
        </Text>
    </View>
  );
}

function Selector({ title, onItemSelected, selectedItem, marginTop }) {
  return (
    <View style={{ marginTop: marginTop }}>
      <Text style={{ paddingLeft: "9%" }}>{title}</Text>
      <Divider />
      <View
        style={{
          flexDirection: "row",
          justifyContent: "space-evenly",
          paddingLeft: "5%",
          paddingRight: "5%",
          marginTop: "2%",
        }}
      >
        {[1, 2, 3, 4, 5, "more"].map((d, i) => {
          return (
            <TouchableOpacity
              key={`more-${i}`}
              onPress={() => {
                onItemSelected(d);
              }}
            >
              <BlockRadioSelector selected={d === selectedItem} text={d} />
            </TouchableOpacity>
          );
        })}
      </View>
    </View>
  );
}

function BlockRadioSelector({ text, selected }) {
  return (
    <View
      style={{
        height: 48,
        width: 48,
        borderRadius: 8,
        borderColor: "#909090",
        borderWidth: 1,
        backgroundColor: selected ? "#E72E68" : "transparent",
        justifyContent: "center",
      }}
    >
      <Text
        style={{ textAlign: "center", color: !selected ? "#909090" : "white" }}
      >
        {text}
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  containerStyle: {
    // padding:"9%"
  },

  headerQuestTextStyle: {
    fontWeight: "bold",
    padding: "9%",
  },

  subQuestionStyle: {
    fontSize: 14,
    textAlign: "left",
    paddingLeft: "9%",
    paddingRight: "9%",
    paddingTop: "9%",
    paddingBottom: "5%",
  },
});
