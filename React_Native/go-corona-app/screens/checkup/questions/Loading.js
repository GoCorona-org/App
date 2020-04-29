import React from 'react';
import { ActivityIndicator, StyleSheet, Text, View } from 'react-native';

const Loading = () => {
    return (
        <View style={styles.container}>
            <ActivityIndicator />
        </View>
    );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fafafa',
    justifyContent: 'center',
    alignItems: 'center',
    alignContent: 'center'
  }
})

export default Loading;