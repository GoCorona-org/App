import React, { Component } from "react";
import { StyleSheet, View, Text, Image } from "react-native";
import {
  ScrollView,
  RectButton,
  TouchableOpacity,
} from "react-native-gesture-handler";
import InternationalTravelImage from "../../../../assets/images/InternationalTravel.svg";
import { Divider } from "react-native-material-ui";
import { Input } from "react-native-elements";
import Http from "../../../../services/Http";
import Autocomplete from "react-native-autocomplete-input";
import {RadioButton} from "react-native-paper";
import Axios from "axios";
import { Ionicons } from "@expo/vector-icons";

export default function International({questions,setValues}) {
  const [yesChecked, setYesChecked] = React.useState(questions.internationTravel);
  const [searchResults, setSearchResults] = React.useState([]);
  const [selectedCountry, setSelectedCountry] = React.useState("");
  const [addedCountries, setAddedCountries] = React.useState(questions.visitedCountries);

  const onSearchCountries = (text) => {
    Axios.get(
      "https://restcountries.eu/rest/v2/name/" + text.replace(/\s/g, "%20")
    ).then((response) => {
      var results = [];
      console.log(response);
      response.data.forEach(({ name }) => results.push(name));
      console.log(results);
      setSearchResults(results);
    })
      .catch((error) => {
        // TODO 
      });
  };

  const onAddCountryClicked = (data) => {
    var countries = [];
    addedCountries.forEach((a) => countries.push(a));
    countries.push(data);
    setAddedCountries(countries);
    setValues({visitedCountries : countries})
  };

  const onRemoveClicked = (index) => {
    var addedCounts = []
    addedCountries.forEach((v, i) => {
      if (i != index) {
        addedCounts.push(v)
      }
    });
    setAddedCountries(addedCounts);
    setValues({visitedCountries : countries})
  }
  return (
    <ScrollView style={styles.containerStyle}>
      <View style={{ flexDirection: "column" }}>
        <Text style={styles.headerQuestTextStyle}>
          Have you travelled anywhere abroad in the last 20 days?
        </Text>
        <InternationalTravelImage
          width="340"
          height="100"
          style={{ alignSelf: "center" }}
        />
        <Text style={styles.subQuestionStyle}>
          Or even arrived in India recently in the last 20 days?
        </Text>
        <View style={{ marginTop: 20 }}>
          <Divider />
        </View>

        <View style={styles.radAlign}>
          <RadioButton.Android
            onPress={() => {
              setYesChecked("no");
              setValues({internationTravel: "no"})
            }}
            value={"No"}
            status={yesChecked === "no" ? "checked" : "unchecked"}
            color="#E03D51"
            uncheckedColor="#D2D2D2"
          />
          <Text>No</Text>
        </View>

        <Divider />

        <View style={styles.radAlign}>
        <RadioButton.Android
            onPress={() => {
              setYesChecked("yes");
              setValues({internationTravel: "yes"})
            }}
            value={"No"}
            status={yesChecked === "yes" ? "checked" : "unchecked"}
            color="#E03D51"
            uncheckedColor="#D2D2D2"
          />
          <Text>Yes</Text>
        </View>

        <Divider />

        {yesChecked==="yes" ? (
          <View style={{ paddingBottom: 200 }}>
            <Text style={styles.subQuestionStyle}>
              If yes, then select the countries you visited before coming to India
          </Text>
            <Autocomplete
              label="Search Country"
              data={searchResults}
              defaultValue={selectedCountry}
              onChangeText={(text) => {
                setSelectedCountry(text);
                onSearchCountries(text);
              }}
              renderItem={({ item, i }) => {
                return (
                  <TouchableOpacity
                    onPress={() => {
                      setSelectedCountry(item);
                      setSearchResults([]);
                    }}
                    style={{ padding: 8 }}
                  >
                    <Text style={{ fontSize: 14 }}>{item}</Text>
                  </TouchableOpacity>
                );
              }}
            />
            <RectButton
              style={{
                width: 112,
                height: 36,
                backgroundColor: "#A9E7CB",
                borderColor: "#909090",
                borderWidth: 1,
                borderRadius: 8,
                marginTop: 32,
                marginLeft: "5%",
                justifyContent: "center",
              }}
              onPress={() => {
                onAddCountryClicked(selectedCountry);
              }}
            >
              <Text
                style={{
                  textAlign: "center",
                  fontWeight: "bold",
                  color: "#989898",
                }}
              >
                ADD
            </Text>
            </RectButton>

            {addedCountries.map((country, index) => {
              return <View style={{ flexDirection: "row", paddingLeft: 20, paddingTop: 16, paddingRight: 20, justifyContent: "space-between" }}>
                <Text style={{ fontSize: 16 }}>{country}</Text>
                <TouchableOpacity onPress={() => { onRemoveClicked(index) }}>
                  <Ionicons name="md-close" size={20} />
                </TouchableOpacity>

              </View>
            })}
          </View>)
          : null}
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  containerStyle: {
    paddingLeft:"5%",
    paddingRight: "5%"
  },

  noBorder: {
    borderWidth: 0
  },

  headerQuestTextStyle: {
    fontWeight: "bold",
    padding: "5%",
  },
  radAlign: {
    flexDirection: "row",
    alignItems: "center"
  },

  subQuestionStyle: {
    fontSize: 14,
    margin: 20,
    marginTop: 40,
    textAlign: "center",
  }
});
