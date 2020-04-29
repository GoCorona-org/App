import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import VirusImg from '../../../assets/images/Virus';
import VirusPercentageSVG from '../components/VirusPercentage';

const CheckupResult = ({result = {}}) => {
  const percenatage = Math.round(result.score) || 40;
  const p = (percenatage / 100) * 180;

  const getResultMessgae = () => {
    if (percenatage > 40) {
      return `Please stay quarantined at home`;
    } else {
      return `Your mostly safe, but be careful`;
    }
  }

  return (
    <View style={styles.container}>
      <View style={{ flexDirection: 'row', paddingTop: 50, alignItems: 'center', justifyContent: 'flex-start' }}>
        <View style={{ height: 180, justifyContent: 'flex-end', marginLeft: -50, paddingRight: 30 }}>
          <Text style={styles.probabilty}>{`${percenatage}%`}</Text>
          <Text style={styles.probabiltyLabel}>probabilty</Text>
        </View>
        <VirusPercentageSVG result={result.score} width={180} height={180}></VirusPercentageSVG>
      </View>
      <View style={{ paddingTop: 40 }}>
        <Text style={styles.quarantined}> {getResultMessgae()} </Text>
      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 30,
    alignItems: 'center'
  },
  probabilty: {
    fontSize: 20,
    fontWeight: "bold",
    textAlign: 'right'
  },
  probabiltyLabel: {
    fontSize: 13,
    fontWeight: "bold"
  },
  quarantined: {
    fontSize: 16,
    fontWeight: "bold"
  }
})

export default CheckupResult