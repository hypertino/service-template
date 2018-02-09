package com.hypertino.example

import scaldi.Module

class ExampleServiceModule extends Module {
  bind[ExampleService] identifiedBy 'exampleService to injected[ExampleService]
}
