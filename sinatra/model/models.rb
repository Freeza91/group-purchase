class User < ActiveRecord::Base
  attr_accessor :tel, :password, :username, :token

   def to_json
    hash = {}
    self.instance_variables.each do |var|
      hash[var.to_s.split('@')[1]] = self.instance_variable_get var
    end
    JSON.parse hash.to_json
  end

end
