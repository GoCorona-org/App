import React, { Component } from 'react';
import { View, Text, Platform, ProgressBarAndroid, ProgressViewIOS, StyleSheet } from 'react-native';

import Separator from "./Separator";

const ProgressBar = ({ textOnTop, currPage = 1, totalPages = 1 }) => {
  const progress = currPage / totalPages

  return (
    <View style={styles.container}>
      <Separator />
      <View style={{ flex: 1 }}>
        <Text style={styles.text}>{textOnTop}</Text>
      </View>
      {
        (Platform.OS === 'android')
          ?
          (<View style={styles.progressAndView}>
            <ProgressBarAndroid progress={progress} styleAttr="Horizontal" indeterminate={false} style={styles.progressAnd} />
          </View>)
          :
          (<View style={styles.progressIosView}>
            <ProgressViewIOS progress={progress} style={styles.progressIos} progressTintColor="#49D581" />
          </View>)
      }
    </View>
  );
};

export default ProgressBar;

const styles = StyleSheet.create(
  {
    container:
    {
      flex: 1,
      flexDirection: 'row',
      justifyContent: 'center',
      paddingTop: (Platform.OS === 'ios') ? 12 : 0,
      paddingHorizontal: 25,
      backgroundColor: '#EEEEEE',
    },

    text:
    {
      color: 'black',
      fontSize: 15,
      marginBottom: 15,
      textAlign: 'left'
    },

    progressAndView: {
      flex: 1
    },

    progressIosView: {
      flex: 1,
      marginTop: 6
    },

    progressAnd: {
      width: '50%',
      color: '#49D581',
      paddingTop: 50
    },

    progressIos: {
      width: '50%',
      alignSelf: 'flex-end'
    }

  });